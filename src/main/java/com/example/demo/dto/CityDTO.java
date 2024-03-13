package com.example.demo.dto;

import com.example.demo.model.Address;

import java.util.List;

public class CityDTO {
    private int id;
    private String  name;
    private List<Address> addressList;

    public CityDTO() {
    }

    public CityDTO(int id, String name, List<Address> addressList) {
        this.id = id;
        this.name = name;
        this.addressList = addressList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }
}
