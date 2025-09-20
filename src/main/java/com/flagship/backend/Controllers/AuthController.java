package com.flagship.backend.Controllers;

import com.flagship.backend.DTO.LoginUserRequest;
import com.flagship.backend.DTO.RegisterUserRequest;
import com.flagship.backend.DTO.UserInfo;
import com.flagship.backend.Entities.User;
import com.flagship.backend.Respositories.UserRepository;
import com.flagship.backend.Services.RegisterUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final RegisterUserService registerUserService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public AuthController(RegisterUserService registerUserService, AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.registerUserService = registerUserService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterUserRequest registerUserRequest) {
        registerUserService.register(registerUserRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("User registered successfully");

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginUserRequest loginUserRequest, HttpServletRequest httpServletRequest) {

        String username = loginUserRequest.username();
        String password = loginUserRequest.password();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        try {
            Authentication authentication = authenticationManager.authenticate(token);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            HttpSession session = httpServletRequest.getSession(true);
            session.setAttribute(
                    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    SecurityContextHolder.getContext()
            );

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Logged in");
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Bad Credentials");
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Unauthorized");
        }

        Optional<User> user = userRepository.findUserByUsername(userDetails.getUsername());

        UserInfo userInfo = new UserInfo(user.get());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userInfo);
    }
}
