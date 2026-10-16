package com.teamz.customer.service;

import com.teamz.customer.entity.User;
import com.teamz.customer.entity.UserRole;
import com.teamz.customer.exceptions.UserAlreadyExistException;
import com.teamz.customer.repository.UserRepository;
import com.teamz.customer.utils.AuthResponse;
import com.teamz.customer.utils.LoginRequest;
import com.teamz.customer.utils.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest registerRequest) {
            var user = User.builder()
                    .name(registerRequest.getName())
                    .email(registerRequest.getEmail())
                    .userName(registerRequest.getUserName())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .role(registerRequest.getUserRole()==null?UserRole.USER:registerRequest.getUserRole())
                    .build();

        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new UserAlreadyExistException("A user with the provided email already exists.");
        }
        else {
            User savedUser = userRepository.save(user);
            var accessToken = jwtService.generateToken(savedUser);
            var refreshToken = refreshTokenService.createRefreshToken(savedUser.getEmail());

            return AuthResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken.getRefreshToken())
                    .build();
        }
    }


    public AuthResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        var accessToken = jwtService.generateToken(user);
        var refreshToken = refreshTokenService.createRefreshToken(loginRequest.getEmail());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }
}

