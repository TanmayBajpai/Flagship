package com.flagship.backend.Services;

import com.flagship.backend.DTO.RegisterUserRequest;
import com.flagship.backend.Entities.User;
import com.flagship.backend.Exceptions.UsernameTakenException;
import com.flagship.backend.Respositories.UserRepository;
import com.flagship.backend.Util.HashUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegisterUserRequest registerUserRequest) {

        String normalized = registerUserRequest.username().trim();

        if (userRepository.existsByUsername(normalized)) {
            throw new UsernameTakenException();
        }

        User user = new User();
        user.setUsername(normalized);
        user.setPassword(passwordEncoder.encode(registerUserRequest.password()));
        String apiKey = HashUtil.generateApiKey();
        user.setApiKey(apiKey);

        userRepository.save(user);
    }
}
