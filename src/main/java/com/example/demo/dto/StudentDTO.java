package com.example.demo.dto;

import com.example.demo.model.Address;

import java.time.LocalDate;

public class StudentDTO {
    private Integer id;
    private String name;
    private String email;
    private LocalDate birth;
    private Address address;

    public StudentDTO() {
    }

    public StudentDTO(Integer id, String name, String email, LocalDate birth, Address address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birth = birth;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
