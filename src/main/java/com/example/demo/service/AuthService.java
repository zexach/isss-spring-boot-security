package com.example.demo.service;

import com.example.demo.config.AuthenticationResponse;
import com.example.demo.config.JwtService;
import com.example.demo.enums.Role;
import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import com.example.demo.request.LoginRequest;
import com.example.demo.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final StudentRepository studentRepository;
    private final StudentService studentService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        Student student = studentService.addNewStudent(request);

        String jwtToken = jwtService.generateJwtToken(student);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        Student student = studentRepository.findStudentByEmail(request.getEmail())
                .orElseThrow();

        String jwtToken = jwtService.generateJwtToken(student);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
