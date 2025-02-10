package com.example.backend.reponsitory.Locations;

import com.example.backend.enity.Districts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DistrictReponsitory extends JpaRepository<Districts,Long> {

    List<Districts> findByProvinceId(Long province_id);
}
