package dev.am.am.controllers;

import dev.am.am.dto.security.RecoverPasswordDTO;
import dev.am.am.services.EmailService;
import dev.am.am.dto.security.ResetPasswordDTO;
import dev.am.am.models.EmailModel;
import dev.am.am.models.ResetPasswordModel;
import dev.am.am.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    EmailService emailService;

    @Autowired
    UserService userService;

    @PostMapping("/recoverpassword")
    public ResponseEntity<EmailModel> sendingEmail(@RequestBody @Valid RecoverPasswordDTO recoverPasswordDTO){
       EmailModel emailModel = userService.recoverPassword(recoverPasswordDTO.email());
        return new ResponseEntity<>(emailModel, HttpStatus.CREATED);
    }

    @PostMapping("/resetpassword")
    public ResponseEntity<ResetPasswordModel> sendingEmail(@RequestBody @Valid ResetPasswordDTO resetPasswordDTO){
        ResetPasswordModel resetPasswordModel = userService.resetPassword(resetPasswordDTO.token(),resetPasswordDTO.password());
        return new ResponseEntity<>(resetPasswordModel, HttpStatus.CREATED);
    }
}
