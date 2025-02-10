package com.example.backend.reponsitory;

import com.example.backend.enity.ActivityLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityLogEntityResponsitory extends JpaRepository<ActivityLogEntity, Long> {
}
