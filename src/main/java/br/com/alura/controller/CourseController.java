package br.com.alura.controller;

import br.com.alura.enums.CourseStatus;
import br.com.alura.record.CourseOperationResponse;
import br.com.alura.record.GetCourseResponse;
import br.com.alura.record.RegisterResponse;
import br.com.alura.service.CourseService;
import br.com.alura.vo.CourseNPSReport;
import br.com.alura.vo.CourseVO;
import br.com.alura.vo.RatingVO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alura/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerCourse(@RequestBody CourseVO courseVO) {
        courseService.registerCourse(courseVO);
        RegisterResponse response = new RegisterResponse("Course registered successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    public ResponseEntity<GetCourseResponse> getCoursesByStatus(@RequestParam(name = "status", required = false) CourseStatus status, Pageable pageable) {
        GetCourseResponse response = courseService.getCoursesByStatus(status, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/deactivate/{code}")
    public ResponseEntity<CourseOperationResponse> deactivateCourse(@PathVariable String code) {
        CourseOperationResponse response = courseService.deactivateCourse(code);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/ratings/{courseCode}")
    public ResponseEntity<RegisterResponse> rateCourse(
            @RequestBody RatingVO ratingVO) {
        courseService.rateCourse(ratingVO);
        RegisterResponse response = new RegisterResponse("Rating submitted successfully.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ratings/{courseCode}")
    public ResponseEntity<List<CourseNPSReport>> getCourseRatings(@PathVariable String courseCode) {
        List<CourseNPSReport> ratings = courseService.getCourseRatings(courseCode);
        return ResponseEntity.ok(ratings);
    }
}
