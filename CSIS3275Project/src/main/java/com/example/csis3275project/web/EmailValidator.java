package com.example.csis3275project.web;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EmailValidator implements Predicate<String> {
    @Override
    public boolean test(String s) {
        String regexPattern = "^(.+)@student.douglascollege.ca";
//        String regexPattern = "^(.+).com";
        return Pattern.compile(regexPattern)
                .matcher(s)
                .matches();
    }
}
