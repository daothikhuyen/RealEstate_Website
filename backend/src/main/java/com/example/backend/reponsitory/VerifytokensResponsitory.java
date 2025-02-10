package com.example.backend.reponsitory;

import com.example.backend.enity.Verifytokens;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VerifytokensResponsitory extends JpaRepository<Verifytokens, Long> {

    Optional<Verifytokens> findByEmailAndToken(String email, String token);

    List<Verifytokens> findByEmail(String email);

    Long deleteByEmail(String email);
}
