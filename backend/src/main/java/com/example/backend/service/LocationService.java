package com.example.backend.service;

import com.example.backend.enity.Districts;
import com.example.backend.enity.Provinces;
import com.example.backend.enity.Wards;
import com.example.backend.mapper.LocationMapper;
import com.example.backend.reponsitory.Locations.DistrictReponsitory;
import com.example.backend.reponsitory.Locations.ProvinceRsponsitory;
import com.example.backend.reponsitory.Locations.WardReponsitory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor // lombok tạo các contructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true) // đưa các final thành private nếu null
public class LocationService {

    ProvinceRsponsitory provinceRsponsitory;
    DistrictReponsitory districtReponsitory;
    WardReponsitory wardReponsitory;
    LocationMapper locationMapper;
    public List<Provinces> get_Provinces() {
        return provinceRsponsitory.findAll().stream().toList();
    }

    public List<Districts> get_District(Districts request) {
        Districts districts = locationMapper.toDistricts(request);

        return districtReponsitory.findByProvinceId(districts.getProvinceId()).stream().toList();
    }

    public List<Wards> get_Wards(Wards request) {
        Wards wards = locationMapper.toWards(request);

        return wardReponsitory.findByDistrictId(wards.getDistrictId());
    }
}
