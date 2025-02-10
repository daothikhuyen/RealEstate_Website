package com.example.backend.reponsitory;

import com.example.backend.enity.PasswordRestToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PasswordResetResponsitory extends JpaRepository<PasswordRestToken, Long> {

    List<PasswordRestToken> findByEmail(String email);

    Optional<PasswordRestToken> findByToken(String token);
}
