package br.com.alura.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RatingVO {

    private UserRegistrationVO user;
    private CourseVO course;
    private Integer score;
    private String comment;
}
