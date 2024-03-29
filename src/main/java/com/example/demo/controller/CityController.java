package com.example.demo.controller;

import com.example.demo.dto.CityDTO;
import com.example.demo.model.City;
import com.example.demo.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/city")
@RequiredArgsConstructor
public class CityController {
    private final CityService cityService;

    @GetMapping
    public List<CityDTO> getCities() {
        return cityService.getCities();
    }

    @DeleteMapping(path = "/{cityID}")
    public String deleteCity(@PathVariable("cityID") Integer id){
        cityService.deleteCity(id);
        return "City deleted successfully!";
    }


}
