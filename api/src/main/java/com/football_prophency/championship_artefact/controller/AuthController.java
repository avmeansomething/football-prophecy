package com.football_prophency.championship_artefact.controller;

import com.football_prophency.championship_artefact.config.UserDetailsServiceImpl;
import com.football_prophency.championship_artefact.enums.UserRole;
import com.football_prophency.championship_artefact.model.User;
import com.football_prophency.championship_artefact.repository.UserRepository;
import com.football_prophency.championship_artefact.security.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.models.annotations.OpenAPI30;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final UserDetailsServiceImpl userDetailsService;
  private final JwtService jwtService;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public AuthController(AuthenticationManager authenticationManager,
                        UserDetailsServiceImpl userDetailsService,
                        JwtService jwtService,
                        UserRepository userRepository,
                        PasswordEncoder passwordEncoder) {
    this.authenticationManager = authenticationManager;
    this.userDetailsService = userDetailsService;
    this.jwtService = jwtService;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @PostMapping("/login")
  @Operation(description = "Логин пользователя")
  public Map<String, String> login(@RequestBody LoginRequest request) {
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(request.username(), request.password())
    );

    UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());
    String token = jwtService.generateToken(userDetails);

    return Map.of("token", token);
  }

  @PostMapping("/register")
  @Operation(description = "Регистрация нового пользователя")
  public Map<String, String> register(@RequestBody RegisterRequest request) {
    // Создаём твоего User
    User user = new User();
    user.setUsername(request.username());
    user.setPassword(passwordEncoder.encode(request.password()));
    user.setRole(UserRole.PLAYER);
    user.setTotalPoints(0);

    userRepository.save(user);

    UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());
    String token = jwtService.generateToken(userDetails);

    return Map.of("token", token);
  }
}

record LoginRequest(String username, String password) {}
record RegisterRequest(String username, String password) {}
