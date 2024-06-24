package dev.adovgapp.advogapp.controllers;

import dev.adovgapp.advogapp.dto.EnumList;
import dev.adovgapp.advogapp.dto.lawyer.LawyerRequestDTO;
import dev.adovgapp.advogapp.dto.lawyer.LawyerResponseByIdDTO;
import dev.adovgapp.advogapp.dto.lawyer.LawyerResponseDTO;
import dev.adovgapp.advogapp.dto.security.RequestListDTO;
import dev.adovgapp.advogapp.dto.security.ResetPasswordDTO;
import dev.adovgapp.advogapp.dto.security.ResponseListDTO;
import dev.adovgapp.advogapp.enums.Specialization;
import dev.adovgapp.advogapp.exceptions.ApiRequestException;
import dev.adovgapp.advogapp.models.Lawyer;
import dev.adovgapp.advogapp.models.ResetPasswordModel;
import dev.adovgapp.advogapp.services.LawyerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/lawyer")
public class LawyerController {
    @Autowired
    private LawyerService service;


    @PostMapping
    public ResponseEntity<LawyerResponseDTO> addLawyer(@RequestBody LawyerRequestDTO data) {
        try {
            var lawyer = service.save(data);
            LawyerResponseDTO lawyerResponseDTO = new LawyerResponseDTO(lawyer.getId(),lawyer.getUser().getFullName(), Specialization.getByCode(lawyer.getSpecialization()),lawyer.getDescription());
        return ResponseEntity.ok().body(lawyerResponseDTO);
        } catch(AuthenticationException authenticationException){
            throw new ApiRequestException(authenticationException.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/list")
    public ResponseEntity<ResponseListDTO<LawyerResponseDTO>> lawyerListByPage(@RequestBody RequestListDTO data) {
        try {
            Page<Lawyer> lawyerPage = service.findAllByPage(data.page(), data.totalRecords());
            List<LawyerResponseDTO> lawyerPageDTO = service.convertToListDTO(lawyerPage);
            ResponseListDTO<LawyerResponseDTO> responseListDTO = new ResponseListDTO<LawyerResponseDTO>(lawyerPageDTO,lawyerPage.getTotalElements());
            return ResponseEntity.ok().body(responseListDTO);
        } catch(AuthenticationException authenticationException){
            throw new ApiRequestException(authenticationException.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<LawyerResponseByIdDTO> getLawyerById(@RequestBody @PathVariable String id) {
        try {
            var lawyer = service.findById(id);
            if(lawyer.isEmpty()) {
                throw new ApiRequestException("Advogado Não Cadastrado",HttpStatus.BAD_REQUEST);
            }
            LawyerResponseByIdDTO lawyerResponseByIdDTO = service.convertToByIdDTO(lawyer.get());
            return ResponseEntity.ok().body(lawyerResponseByIdDTO);
        } catch(AuthenticationException authenticationException){
            throw new ApiRequestException(authenticationException.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> editLawyerById(@RequestBody LawyerRequestDTO lawyerRequestDTO) {
        try {
            service.updateLawyer(lawyerRequestDTO);
            return ResponseEntity.ok().body("ok");
        } catch(AuthenticationException authenticationException){
            throw new ApiRequestException(authenticationException.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/specializations")
    public ResponseEntity<ResponseListDTO> getSpecializations() {
        try {
            List<EnumList> specializations = Specialization.getList();

            ResponseListDTO responseListDTO = new ResponseListDTO(specializations, (long) specializations.size());
            return ResponseEntity.ok().body(responseListDTO);
        } catch(AuthenticationException authenticationException){
            throw new ApiRequestException(authenticationException.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

}
