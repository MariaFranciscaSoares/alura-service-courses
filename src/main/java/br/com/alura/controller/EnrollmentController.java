package br.com.alura.controller;

import br.com.alura.record.RegisterResponse;
import br.com.alura.service.EnrollmentService;
import br.com.alura.vo.EnrollmentVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alura/enrollment")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping()
    public ResponseEntity<RegisterResponse> enrollUserInCourse(@RequestBody EnrollmentVO enrollmentVO) {
        enrollmentService.enrollUserInCourse(enrollmentVO);
        RegisterResponse response = new RegisterResponse("User enrolled successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
