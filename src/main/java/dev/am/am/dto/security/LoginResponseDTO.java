package dev.am.am.dto.security;

import java.util.Optional;

public record LoginResponseDTO(String token, String userId, String role) {
}
