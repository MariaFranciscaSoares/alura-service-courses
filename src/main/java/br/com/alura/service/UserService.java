package br.com.alura.service;

import br.com.alura.record.GetUserResponse;
import br.com.alura.vo.UserRegistrationVO;

public interface UserService {

    void registerUser(UserRegistrationVO user);

    GetUserResponse getUserByUsername(String username);
}
