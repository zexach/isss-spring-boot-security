package com.example.demo.request;


import com.example.demo.enums.Role;
import com.example.demo.model.Address;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private LocalDate birth;
    private Address address;
}
