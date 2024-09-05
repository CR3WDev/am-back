package dev.am.am.controllers;

import dev.am.am.dto.security.LoginResponseDTO;
import dev.am.am.exceptions.ApiRequestException;
import dev.am.am.repositories.UserRepository;
import dev.am.am.dto.security.LoginRequestDTO;
import dev.am.am.dto.security.RegisterDTO;
import dev.am.am.enums.UserRole;
import dev.am.am.infra.security.TokenService;
import dev.am.am.models.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        try {
            var auth = this.authenticationManager.authenticate(usernamePassword);
            User userAuth = (User) auth.getPrincipal();
            var token = tokenService.generateToken(userAuth);
            return ResponseEntity
                    .ok(new LoginResponseDTO(token, userAuth.getId(), userAuth.getRole().getName()));
        } catch (AuthenticationException authenticationException) {
            throw new ApiRequestException("Usuário ou senha inválido!", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO data) {
        Optional<User> user = this.repository.findByEmail(data.email());
        if (user.isPresent()) {
            throw new ApiRequestException("Email já cadastrado!", HttpStatus.BAD_REQUEST);
        }
        String encrytpedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(null, data.fullName(), data.email(), encrytpedPassword, UserRole.USER);
        this.repository.save(newUser);
        return ResponseEntity.ok().build();
    }
}
