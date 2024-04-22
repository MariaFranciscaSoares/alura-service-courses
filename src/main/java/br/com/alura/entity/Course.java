package br.com.alura.entity;

import br.com.alura.enums.CourseStatus;
import br.com.alura.vo.CourseVO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @Column(unique = true, nullable = false, length = 10)
    private String code;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "instructor_id", nullable = false)
    private User instructor;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CourseStatus status;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @Column(name = "inactivation_date")
    private LocalDate inactivationDate;

    @OneToMany(mappedBy = "course")
    private List<Rating> ratings;

    public Course(CourseVO courseVO) {
        this.code = courseVO.getCode();
        this.name = courseVO.getName();
        this.instructor = courseVO.getInstructor();
        this.description = courseVO.getDescription();
        this.status = courseVO.getStatus();
        this.creationDate = courseVO.getCreationDate();
        this.inactivationDate = courseVO.getInactivationDate();
    }
}
