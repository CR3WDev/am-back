package dev.am.am.services;

import dev.am.am.repositories.ResetPasswordRepository;
import dev.am.am.models.ResetPasswordModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class ResetPasswordService {

    @Autowired
    ResetPasswordRepository resetPasswordRepository;

    public Optional<ResetPasswordModel> validateToken(UUID userId, String token) {
        return resetPasswordRepository.validateToken(userId, token);
    }

    public ResetPasswordModel generateToken(UUID userId) {
        ResetPasswordModel resetPasswordModel = new ResetPasswordModel();
        resetPasswordModel.setToken(UUID.randomUUID().toString());
        resetPasswordModel.setUserId(userId);
        resetPasswordModel.setExpirationTime(LocalDateTime.now().plusHours(1).toString());
        return this.resetPasswordRepository.save(resetPasswordModel);
    }

    public Optional<ResetPasswordModel> findByToken(String token) {
        return resetPasswordRepository.findByToken(token);
    }
}
