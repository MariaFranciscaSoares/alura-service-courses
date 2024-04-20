package br.com.alura.entity;

import br.com.alura.enums.Role;
import br.com.alura.vo.UserRegistrationVO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private String name;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDateTime creationDate;

    public User(UserRegistrationVO userRegistrationVO) {
        this.name = userRegistrationVO.getName();
        this.username = userRegistrationVO.getUsername();
        this.email = userRegistrationVO.getEmail();
        this.password = userRegistrationVO.getPassword();
        this.role = userRegistrationVO.getRole();
        this.creationDate = LocalDateTime.now();
    }
}
