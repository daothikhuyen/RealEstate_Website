package com.example.backend.reponsitory;


import com.example.backend.enity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByIdAndIsDeleted(Long userId, int i);
    boolean existsByPhone(String phone);

    Page<User> findAllByIsDeleted(int i, Pageable paging);

}
