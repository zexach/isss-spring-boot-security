package com.example.demo.service;

import com.example.demo.config.AuthenticationResponse;
import com.example.demo.config.JwtService;
import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import com.example.demo.request.LoginRequest;
import com.example.demo.request.RegisterRequest;
import com.example.demo.validators.EmailValidator;
import com.example.demo.validators.PasswordValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final StudentRepository studentRepository;
    private final StudentService studentService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailValidator emailValidator;
    private final PasswordValidator passwordValidator;

    public AuthenticationResponse register(RegisterRequest request) {

        if(!emailValidator.isEmailValid(request.getEmail())) {
            throw new IllegalStateException("Email is not valid");
        }
        if(!passwordValidator.isPasswordValid(request.getPassword())){
            throw new IllegalStateException("Password is not valid");
        }

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
