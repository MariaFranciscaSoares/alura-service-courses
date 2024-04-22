package br.com.alura.service.impl;

import br.com.alura.entity.Course;
import br.com.alura.entity.Rating;
import br.com.alura.enums.CourseStatus;
import br.com.alura.enums.Role;
import br.com.alura.exception.AluraException;
import br.com.alura.record.CourseOperationResponse;
import br.com.alura.record.GetCourseResponse;
import br.com.alura.repository.CourseRepository;
import br.com.alura.repository.RatingRepository;
import br.com.alura.service.CourseService;
import br.com.alura.service.EmailSenderService;
import br.com.alura.util.CommonsUtil;
import br.com.alura.vo.CourseNPSReport;
import br.com.alura.vo.CourseVO;
import br.com.alura.vo.RatingVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final RatingRepository ratingRepository;
    private final EmailSenderService emailSenderService;

    public CourseServiceImpl(CourseRepository courseRepository, RatingRepository ratingRepository, EmailSenderService emailSenderService) {
        this.courseRepository = courseRepository;
        this.ratingRepository = ratingRepository;
        this.emailSenderService = emailSenderService;
    }

    @Override
    public void registerCourse(CourseVO courseVO) {
        try {
            performValidations(courseVO);
            Course course = new Course(courseVO);
            courseRepository.save(course);
            log.info("Course registered successfully");
        } catch (DataIntegrityViolationException e) {
            log.error("Data integrity error: the operation failed due to a data integrity constraint violation.", e);
            throw new AluraException(e.getMessage(), e);
        } catch (Exception e) {
            log.error("Failed to register course", e);
            throw new AluraException(e.getMessage(), e);
        }
    }

    @Override
    public GetCourseResponse getCoursesByStatus(CourseStatus status, Pageable pageable) {
        try {
            Page<Course> coursesPage = courseRepository.findByStatus(status, pageable);

            if (CommonsUtil.isNullOrEmpty(coursesPage.getContent())) {
                return new GetCourseResponse(null, "No courses found.");
            }

            return convertCoursesToResponse(coursesPage.getContent());
        } catch (Exception e) {
            log.error("Failed to fetch course data.", e);
            throw new AluraException(e.getMessage(), e);
        }
    }

    @Override
    public CourseOperationResponse deactivateCourse(String code) {

        Optional<Course> optionalCourse = courseRepository.findById(code);

        if (optionalCourse.isEmpty()) {
            return new CourseOperationResponse("Course not found with ID: " + code);
        }

        Course course = optionalCourse.get();

        if (CourseStatus.INATIVO.equals(course.getStatus())) {
            return new CourseOperationResponse("The course is already inactive.");
        }

        course.setInactivationDate(LocalDate.now());
        course.setStatus(CourseStatus.INATIVO);

        courseRepository.save(course);

        return new CourseOperationResponse("Course deactivated successfully.");
    }

    @Override
    public void rateCourse(RatingVO ratingVO) {

        Course course = courseRepository.findByCode(ratingVO.getCourse().getCode())
                .orElseThrow(() -> new AluraException("Course not found with code: " + ratingVO.getCourse().getCode()));

        checkIfCourseIsActive(course);

        Rating rating = new Rating(ratingVO);

        ratingRepository.save(rating);

        if (rating.getScore() < 6) {
            emailSenderService.send(ratingVO);
        }
    }

    @Override
    public List<CourseNPSReport> getCourseRatings(String courseCode) {
        List<Course> coursesWithEnoughEnrollments = courseRepository.findCoursesWithEnoughEnrollments();

        List<CourseNPSReport> courseNPSReports = new ArrayList<>();
        for (Course course : coursesWithEnoughEnrollments) {
            double nps = calculateNPS(course);
            CourseNPSReport report = new CourseNPSReport(course.getName(), nps);
            courseNPSReports.add(report);
        }

        return courseNPSReports;
    }

    private void performValidations(CourseVO courseVO) {
        isValidCourseCode(courseVO.getCode());
        checkIfCourseExists(courseVO.getCode());
        validateInstructorRole(courseVO.getInstructor().getRole());
        validateInactivationDateForActiveCourse(courseVO.getStatus(), courseVO.getInactivationDate());
    }

    private void isValidCourseCode(String code) {
        if (!CommonsUtil.isValidCourseCode(code)) {
            throw new AluraException("The course code does not meet the required criteria.");
        }
    }

    private void checkIfCourseExists(String code) {
        if (courseRepository.existsByCode(code)) {
            throw new AluraException("Course already exists.");
        }
    }

    public GetCourseResponse convertCoursesToResponse(List<Course> courses) {
        List<CourseVO> courseVOs = courses.stream()
                .map(CourseVO::new)
                .collect(Collectors.toList());
        return new GetCourseResponse(courseVOs, "Courses retrieved successfully");
    }

    private static void validateInstructorRole(Role role) {
        if (!Role.INSTRUCTOR.equals(role)) {
            throw new AluraException("Only instructors can register courses.");
        }
    }

    private void validateInactivationDateForActiveCourse(CourseStatus status, LocalDate inactivationDate) {
        if (CourseStatus.ATIVO.equals(status) && !CommonsUtil.isNullOrEmpty(inactivationDate)) {
            throw new AluraException("The inactivation date can only be set when the course is inactive.");
        }
    }

    private static void checkIfCourseIsActive(Course course) {
        if (CourseStatus.ATIVO != course.getStatus()) {
            throw new AluraException("Cannot rate an inactive course.");
        }
    }

    private double calculateNPS(Course course) {
        List<Rating> ratings = course.getRatings();
        int promoters = 0;
        int detractors = 0;
        for (Rating rating : ratings) {
            int score = rating.getScore();
            if (score >= 9 && score <= 10) {
                promoters++;
            } else if (score >= 0 && score <= 6) {
                detractors++;
            }
        }

        int totalResponses = promoters + detractors;
        if (totalResponses == 0) {
            return 0;
        }

        double nps = (promoters - detractors) / (double) totalResponses * 100;
        return Math.round(nps * 10) / 10.0;
    }

}
