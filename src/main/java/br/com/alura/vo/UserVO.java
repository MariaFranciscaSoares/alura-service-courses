package br.com.alura.vo;

import br.com.alura.enums.Role;
import lombok.*;

@Data
@AllArgsConstructor
public class UserVO {
    private String name;
    private String email;
    private Role role;
}
