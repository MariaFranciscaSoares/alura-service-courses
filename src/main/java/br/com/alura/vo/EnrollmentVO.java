package br.com.alura.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class EnrollmentVO {

    private UserRegistrationVO user;
    private CourseVO course;
    private LocalDate enrollmentDate;
}
