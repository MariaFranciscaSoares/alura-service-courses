package br.com.alura.service.impl;

import br.com.alura.entity.User;
import br.com.alura.exception.AluraException;
import br.com.alura.record.GetUserResponse;
import br.com.alura.repository.UserRepository;
import br.com.alura.service.UserService;
import br.com.alura.util.CommonsUtil;
import br.com.alura.vo.UserRegistrationVO;
import br.com.alura.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(UserRegistrationVO userRegistrationVO) {
        try {
            performValidations(userRegistrationVO);
            User user = new User(userRegistrationVO);
            userRepository.save(user);
            log.info("User registered successfully");
        } catch (DataIntegrityViolationException e) {
            log.error("Data integrity error: the operation failed due to a data integrity constraint violation.", e);
            throw new AluraException(e.getMessage(), e);
        } catch (Exception e) {
            log.error("Failed to register user", e);
            throw new AluraException(e.getMessage(), e);
        }
    }

    @Override
    public GetUserResponse getUserByUsername(String username) {
        try {
            User user = userRepository.findByUsername(username);

            if (CommonsUtil.isNullOrEmpty(user)) {
                return new GetUserResponse(null, "User not found");
            }

            UserVO userVO = new UserVO(user.getName(), user.getEmail(), user.getRole());
            return new GetUserResponse(userVO, "Query executed successfully");
        } catch (Exception e) {
            log.error("Failed to fetch user data.", e);
            throw new AluraException(e.getMessage(), e);
        }
    }

    private void performValidations(UserRegistrationVO userRegistrationVO) {
        isValidUsername(userRegistrationVO.getUsername());
        isValidEmail(userRegistrationVO.getEmail());
        checkIfUserExists(userRegistrationVO);
    }

    private void isValidUsername(String username) {
        if (CommonsUtil.containsNonLowerCaseCharacters(username)
                || CommonsUtil.containsDigits(username)
                || CommonsUtil.containsSpaces(username)) {
            throw new AluraException("Username must contain only lowercase characters, no digits, and no spaces");
        }
    }

    private void isValidEmail(String email) {
        if (!CommonsUtil.isValidEmail(email)) {
            throw new AluraException("Invalid email address");
        }
    }

    private void checkIfUserExists(UserRegistrationVO userRegistrationVO) {
        if (userRepository.existsByEmailOrUsername(userRegistrationVO.getEmail(), userRegistrationVO.getUsername())) {
            throw new AluraException("Email or username already exists.");
        }
    }
}
