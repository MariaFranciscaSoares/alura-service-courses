package br.com.alura.vo;

import br.com.alura.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRegistrationVO {

    private String name;
    private String username;
    private String email;
    private String password;
    private Role role;
}
