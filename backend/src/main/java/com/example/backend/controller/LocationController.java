package com.example.backend.controller;

import com.example.backend.dto.response.ApiResponse;
import com.example.backend.enity.Districts;
import com.example.backend.enity.Provinces;
import com.example.backend.enity.Wards;
import com.example.backend.service.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
@Slf4j
public class LocationController {

    @Autowired
    LocationService locationService;


    @GetMapping("/get_Provinces")
    public List<Provinces> get_Provinces(){
        return locationService.get_Provinces();
    }

    @PostMapping("/get_Districts")
    public List<Districts> get_Districts(@RequestBody Districts request){
        System.out.println("Hi bạn");
        return locationService.get_District(request);
    }

    @PostMapping("/get_Wards")
    public List<Wards> get_Wards(@RequestBody Wards request){
        return locationService.get_Wards(request);
    }


}
