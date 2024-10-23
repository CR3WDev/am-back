package dev.am.am.controllers;

import dev.am.am.dto.security.*;
import dev.am.am.exceptions.ApiRequestException;
import dev.am.am.models.EmailModel;
import dev.am.am.models.ResetPasswordModel;
import dev.am.am.repositories.UserRepository;
import dev.am.am.enums.UserRole;
import dev.am.am.infra.security.TokenService;
import dev.am.am.models.User;
import dev.am.am.services.EmailService;
import dev.am.am.services.UserService;
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
    @Autowired
    private UserService userService;

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
        Optional<User> user = this.userService.findByEmail(data.email());
        if (user.isPresent()) {
            throw new ApiRequestException("Email já cadastrado!", HttpStatus.BAD_REQUEST);
        }
        this.userService.save(data);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/recoverpassword")
    public ResponseEntity<EmailModel> recoverEmail(@RequestBody @Valid RecoverPasswordDTO recoverPasswordDTO){
        userService.recoverPassword(recoverPasswordDTO.email());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/resetpassword")
    public ResponseEntity<ResetPasswordModel> resetPassword(@RequestBody @Valid ResetPasswordDTO resetPasswordDTO){
        ResetPasswordModel resetPasswordModel = userService.resetPassword(resetPasswordDTO.token(),resetPasswordDTO.password());
        return new ResponseEntity<>(resetPasswordModel, HttpStatus.CREATED);
    }
}
