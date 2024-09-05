package dev.am.am.repositories;

import dev.am.am.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    UserDetails findUserByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findById(UUID id);

}
