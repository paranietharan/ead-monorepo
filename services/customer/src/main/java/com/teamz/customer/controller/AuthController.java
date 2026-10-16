package com.teamz.customer.controller;

import com.teamz.customer.DTO.UserDetailsFromTokenDTO;
import com.teamz.customer.entity.RefreshToken;
import com.teamz.customer.entity.User;
import com.teamz.customer.exceptions.UserAlreadyExistException;
import com.teamz.customer.repository.UserRepository;
import com.teamz.customer.service.AuthService;
import com.teamz.customer.service.JwtService;
import com.teamz.customer.service.RefreshTokenService;
import com.teamz.customer.utils.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthController(AuthService authService, RefreshTokenService refreshTokenService, JwtService jwtService, UserRepository userRepository) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            return ResponseEntity.ok(authService.register(registerRequest));
        }catch (UserAlreadyExistException e){
            return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .body(e.getMessage()); // Include the exception message in the response
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(e.getMessage()); // Include the exception message in the response
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            return ResponseEntity.ok(authService.login(loginRequest));
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Incorrect Username or password");
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {

        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(refreshTokenRequest.getRefreshToken());
        User user = refreshToken.getUser();

        String accessToken = jwtService.generateToken(user);

        return ResponseEntity.ok(AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build());
    }


    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestBody ValidateTokenRequest validateTokenRequest) {
        String token = validateTokenRequest.getToken();
        String username = jwtService.extractUsername(token); // Extract username from token

        // Find the user by username
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        // Validate the token with the user details
        boolean isValid = jwtService.isTokenValid(token, user);

        return ResponseEntity.ok(isValid);
    }

    @PostMapping("/getUserIdFromToken")
    public ResponseEntity<UserDetailsFromTokenDTO> getUserIdFromToken(@RequestBody ValidateTokenRequest validateTokenRequest) {
        String token = validateTokenRequest.getToken();
        String username = jwtService.extractUsername(token); // Extract username from token

        // Find the user by username
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        UserDetailsFromTokenDTO userDetails=UserDetailsFromTokenDTO.builder()
                .userId(user.getUserId())
                .userRole(user.getRole())
                .build();

        return ResponseEntity.ok(userDetails);

    }
}
