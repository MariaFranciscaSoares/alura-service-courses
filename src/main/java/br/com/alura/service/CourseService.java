package br.com.alura.service;

import br.com.alura.enums.CourseStatus;
import br.com.alura.record.CourseOperationResponse;
import br.com.alura.record.GetCourseResponse;
import br.com.alura.vo.CourseNPSReport;
import br.com.alura.vo.CourseVO;
import br.com.alura.vo.RatingVO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseService {

    void registerCourse(CourseVO courseVO);

    GetCourseResponse getCoursesByStatus(CourseStatus status, Pageable pageable);

    CourseOperationResponse deactivateCourse(String code);

    void rateCourse(RatingVO ratingVO);

    List<CourseNPSReport> getCourseRatings(String courseCode);
}
