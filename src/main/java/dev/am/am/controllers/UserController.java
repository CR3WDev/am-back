package dev.am.am.controllers;

import dev.am.am.dto.User.UserRequestDTO;
import dev.am.am.dto.User.UserResponseDTO;
import dev.am.am.exceptions.ApiRequestException;
import dev.am.am.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getLawyerById(@RequestBody @PathVariable String id) {
        try {
            var user = service.findById(id);
            if(user.isEmpty()) {
                throw new ApiRequestException("Advogado Não Cadastrado", HttpStatus.BAD_REQUEST);
            }
            UserResponseDTO userResponseDTO = service.convertToDTO(user.get());
            return ResponseEntity.ok().body(userResponseDTO);
        } catch(AuthenticationException authenticationException){
            throw new ApiRequestException(authenticationException.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping
    public ResponseEntity<String> editUser(@RequestBody UserRequestDTO user) {
        try {
            service.updateUser(user);
            return ResponseEntity.ok().body("Usuário Atualizado!");
        } catch(AuthenticationException authenticationException){
            throw new ApiRequestException(authenticationException.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
