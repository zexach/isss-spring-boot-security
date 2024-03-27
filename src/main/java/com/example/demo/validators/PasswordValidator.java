package com.example.demo.validators;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PasswordValidator {
    private static final String passwordRegex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()-+=])(?=\\S+$).{8,}$";
    private static final Pattern passwordPattern = Pattern.compile(passwordRegex);

    public Boolean isPasswordValid(String password) {
        Matcher matcher = passwordPattern.matcher(password);
        return matcher.matches();
    }


}
