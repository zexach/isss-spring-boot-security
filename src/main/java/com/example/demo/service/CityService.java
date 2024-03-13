package com.example.demo.service;

import com.example.demo.model.City;
import com.example.demo.repository.CityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {
    private final CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public ModelMapper modelMapper;

    public List<City> getCities() {
        return cityRepository.findAll();
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
}
