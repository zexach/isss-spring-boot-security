package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class City {
    @Id
    @GeneratedValue
    private Integer id;
    private String city;
    @OneToMany(mappedBy = "city", fetch = FetchType.EAGER)
    private List<Student> students;

    public City() {
    }

    public City(Integer id, String city, List<Student> students) {
        this.id = id;
        this.city = city;
        this.students = students;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
