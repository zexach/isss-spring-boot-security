package com.example.demo.service;

import com.example.demo.dto.CityDTO;
import com.example.demo.model.City;
import com.example.demo.repository.CityRepository;
import com.example.demo.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;
    public final ModelMapper modelMapper;

    public List<CityDTO> getCities() {
        return cityRepository.findAll()
                .stream()
                .map(this::convertToCityDTO)
                .collect(Collectors.toList());
    }

    public City addCity(RegisterRequest student) {
        City city = this.cityRepository.findCityByName(student.getCity().getName());
        if(city == null) {
            throw new IllegalStateException("City doesn't exist");
        }
        return city;
    }

    public void deleteCity(Integer id) {
        boolean exists = this.cityRepository.existsById(id);
        if(!exists){
            throw new IllegalStateException("City doesn't exist");
        }
        this.cityRepository.deleteById(id);
    }

    public CityDTO convertToCityDTO(City city) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        CityDTO cityDTO = new CityDTO();
        cityDTO = modelMapper.map(city, CityDTO.class);
        return cityDTO;
    }
}
