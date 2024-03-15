package com.example.demo.service;

import com.example.demo.dto.CityDTO;
import com.example.demo.model.City;
import com.example.demo.repository.CityRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CityService {
    private final CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Autowired
    public ModelMapper modelMapper;

    public List<CityDTO> getCities() {
        return cityRepository.findAll()
                .stream()
                .map(this::convertToCityDTO)
                .collect(Collectors.toList());
    }

    public void addCity(City city) {
        Optional<City> optionalCity = cityRepository.findCityByName(city.getName());

        if(optionalCity.isPresent()) {
            throw new IllegalStateException(city.getName() + " is already added");
        }

        cityRepository.save(city);
    }

    public void deleteCity(Integer id) {
        boolean exists = cityRepository.existsById(id);
        if(!exists){
            throw new IllegalStateException("City doesn't exist");
        }
        cityRepository.deleteById(id);
    }

    public CityDTO convertToCityDTO(City city) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        CityDTO cityDTO = new CityDTO();
        cityDTO = modelMapper.map(city, CityDTO.class);
        return cityDTO;
    }
}