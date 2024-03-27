package com.example.demo.validators;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class EmailValidator {
    private static final String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern emailPattern = Pattern.compile(emailRegex);

    public Boolean isEmailValid(String email) {
        Matcher matcher = emailPattern.matcher(email);
        return matcher.matches();
    }
}
