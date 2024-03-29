package com.example.demo.service;

import com.example.demo.model.Address;
import com.example.demo.model.City;
import com.example.demo.repository.AddressRepository;
import com.example.demo.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final CityService cityService;

    public List<Address> getAddresses() {
        return addressRepository.findAll();
    }

    public Address addAddress(RegisterRequest student) {
        Optional<Address> existingAddress = this.addressRepository.
                findAddressByStreetNameAndHouseNumberAndZipCode(
                        student.getAddress().getStreetName(), student.getAddress().getHouseNumber(), student.getAddress().getZipCode()
                );

        if(existingAddress.isPresent()){
            throw new IllegalStateException("Address already exists");
        }

        City city = this.cityService.addCity(student);

        Address userAddress = new Address();
        userAddress.setStreetName(student.getAddress().getStreetName());
        userAddress.setHouseNumber(student.getAddress().getHouseNumber());
        userAddress.setZipCode(student.getAddress().getZipCode());
        userAddress.setCity(city);

        this.addressRepository.save(userAddress);

        return userAddress;
    }
}
