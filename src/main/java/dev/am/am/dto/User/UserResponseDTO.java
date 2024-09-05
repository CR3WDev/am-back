package dev.am.am.dto.User;

import java.util.UUID;

public record UserResponseDTO(UUID id, String fullName, String email) {
}
