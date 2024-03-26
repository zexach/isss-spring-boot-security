package com.example.demo.service;

import com.example.demo.model.Address;
import com.example.demo.repository.AddressRepository;
import com.example.demo.request.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<Address> getAddresses() {
        return addressRepository.findAll();
    }

    public Address addAddress(RegisterRequest student) {
        Optional<Address> existingAddress = addressRepository.
                findAddressByStreetNameAndHouseNumber(student.getAddress().getStreetName(), student.getAddress().getHouseNumber());

        if(existingAddress.isPresent()){
            throw new IllegalStateException("Address already exists");
        }

        Address userAddress = new Address();
        userAddress.setCity(student.getAddress().getCity());
        userAddress.setStreetName(student.getAddress().getStreetName());
        userAddress.setHouseNumber(student.getAddress().getHouseNumber());
        userAddress.setZipCode(student.getAddress().getZipCode());

        addressRepository.save(userAddress);

        return userAddress;
    }
}
