package br.com.alura.controller;

import br.com.alura.record.GetUserResponse;
import br.com.alura.record.RegisterUserResponse;
import br.com.alura.service.UserService;
import br.com.alura.vo.UserRegistrationVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alura/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> registerUser(@RequestBody UserRegistrationVO userRegistrationVO) {
        userService.registerUser(userRegistrationVO);
        RegisterUserResponse response = new RegisterUserResponse("User registered successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{username}")
    public ResponseEntity<GetUserResponse> getUserByUsername(@PathVariable String username) {
        GetUserResponse getUserResponse = userService.getUserByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body(getUserResponse);
    }
}
