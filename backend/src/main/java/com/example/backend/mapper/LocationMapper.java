package com.example.backend.mapper;

import com.example.backend.enity.Districts;
import com.example.backend.enity.Provinces;
import com.example.backend.enity.Wards;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    Districts toDistricts(Districts districts);

    Wards toWards(Wards wards);

    Provinces toProvinces(Provinces request);
}
