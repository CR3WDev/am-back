package dev.am.am.dto.security;

import java.util.UUID;

public record LoginResponseDTO(String token, UUID userId, String role) {
}
