package com.example.backend.reponsitory.Locations;

import com.example.backend.enity.Wards;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WardReponsitory extends JpaRepository<Wards,Long> {

    List<Wards> findByDistrictId(Long district_id);
}
