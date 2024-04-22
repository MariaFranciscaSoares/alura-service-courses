package br.com.alura.vo;

import br.com.alura.entity.Course;
import br.com.alura.entity.User;
import br.com.alura.enums.CourseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CourseVO {

    private String name;
    private String code;
    private User instructor;
    private String description;
    private CourseStatus status;
    private LocalDate creationDate;
    private LocalDate inactivationDate;

    public CourseVO(Course course) {
        this.name = course.getName();
        this.code = course.getCode();
        this.instructor = course.getInstructor();
        this.description = course.getDescription();
        this.status = course.getStatus();
        this.creationDate = course.getCreationDate();
        this.inactivationDate = course.getInactivationDate();
    }
}
