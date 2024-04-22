package br.com.alura.entity;

import br.com.alura.enums.Role;
import br.com.alura.vo.UserRegistrationVO;
import br.com.alura.vo.UserVO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    public User(UserRegistrationVO userRegistrationVO) {
        this.id = userRegistrationVO.getId();
        this.name = userRegistrationVO.getName();
        this.username = userRegistrationVO.getUsername();
        this.email = userRegistrationVO.getEmail();
        this.password = userRegistrationVO.getPassword();
        this.role = userRegistrationVO.getRole();
        this.creationDate = LocalDate.now();
    }

    public User(UserVO userVO) {
        this.name = userVO.getName();
        this.email = userVO.getEmail();
        this.role = userVO.getRole();
    }
}
