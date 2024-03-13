package com.example.demo.service;

import com.example.demo.model.Address;
import com.example.demo.repository.AddressRepository;
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

    public void addAddress(Address address) {
        Optional<Address> optionalAddress = addressRepository.findAddressByHouseNumber(address.getHouseNumber());

        if(optionalAddress.isPresent()){
            throw new IllegalStateException(address.getHouseNumber() + " already exists");
        }

        addressRepository.save(address);
    }
}
