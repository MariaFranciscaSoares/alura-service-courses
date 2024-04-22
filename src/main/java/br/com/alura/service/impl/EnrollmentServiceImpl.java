package br.com.alura.service.impl;

import br.com.alura.entity.Enrollment;
import br.com.alura.enums.CourseStatus;
import br.com.alura.exception.AluraException;
import br.com.alura.repository.EnrollmentRepository;
import br.com.alura.service.EnrollmentService;
import br.com.alura.vo.EnrollmentVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public void enrollUserInCourse(EnrollmentVO enrollment) {

        Enrollment enrollmentEntity = new Enrollment(enrollment);
        checkIfUserAlreadyEnrolled(enrollmentEntity);
        checkIfCourseIsActive(enrollmentEntity);

        enrollmentRepository.save(enrollmentEntity);
    }

    private void checkIfUserAlreadyEnrolled(Enrollment enrollment) {
        if (enrollmentRepository.existsByUserAndCourse(enrollment.getUser(), enrollment.getCourse())) {
            throw new AluraException("User is already enrolled in this course.");
        }
    }

    private void checkIfCourseIsActive(Enrollment enrollment) {
        if (CourseStatus.INATIVO.equals(enrollment.getCourse().getStatus())) {
            throw new AluraException("Cannot enroll in an inactive course.");
        }
    }
}
