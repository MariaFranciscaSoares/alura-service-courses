package br.com.alura.entity;


import br.com.alura.vo.RatingVO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Integer score;

    @Column
    private String comment;

    public Rating(RatingVO ratingVO) {
        this.course = new Course(ratingVO.getCourse());
        this.user = new User(ratingVO.getUser());
        this.score = ratingVO.getScore();
        this.comment = ratingVO.getComment();
    }
}
