package br.com.alura.vo;

import br.com.alura.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UserRegistrationVO {

    private Long id;
    private String name;
    private String username;
    private String email;
    private String password;
    private Role role;
    private LocalDate creationDate;
}
