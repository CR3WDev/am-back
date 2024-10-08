package dev.am.am.repositories;

import dev.am.am.models.EmailModel;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<EmailModel, UUID> {
}
