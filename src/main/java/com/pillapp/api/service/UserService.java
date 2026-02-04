package com.pillapp.api.service;

import com.pillapp.api.domain.entities.User;
import com.pillapp.api.dto.authentication.UserLoginDTO;
import com.pillapp.api.dto.authentication.UserLoginSuccessDTO;
import com.pillapp.api.dto.responses.data.UserProfileDTO;
import com.pillapp.api.dto.authentication.UserRegisterDTO;
import com.pillapp.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.FailedLoginException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService tokenService;
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public void create(UserRegisterDTO dto) {
        userRepository.save(new User(
                dto.email.toLowerCase(),
                dto.userName.toLowerCase(),
                passwordEncoder.encode(dto.password)
        ));
    }

    public UserLoginSuccessDTO login(UserLoginDTO credentials) throws FailedLoginException {

        var email = credentials.email;
        var rawPassword = credentials.password;

        var user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(FailedLoginException::new);

        var encodedPassword = user.password;

        boolean passwordMatched = passwordEncoder.matches(rawPassword, encodedPassword);
        if (!passwordMatched){
            throw new FailedLoginException();
        }

        var response = new UserLoginSuccessDTO();
        response.token = tokenService.generateToken(email);
        response.profile = new UserProfileDTO(user);
        return response;
    }

    //Validators
    public boolean emailExists(String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }

    public boolean userNameExists(String userName) {
        return userRepository.existsByUserNameIgnoreCase(userName);
    }


}
