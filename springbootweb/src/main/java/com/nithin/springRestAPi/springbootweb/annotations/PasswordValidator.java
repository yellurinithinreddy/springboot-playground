package com.nithin.springRestAPi.springbootweb.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Locale;
import java.util.Set;

public class PasswordValidator implements ConstraintValidator<Password,String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return isPassword(s);
    }

    private boolean isPassword(String s) {
        if(s.length() < 10) return false;
        Set<String> specialChars = Set.of(
                "!", "@", "#", "$", "%", "^", "&", "*",
                "(", ")", "_", "+", "-", "=",
                "{", "}", "[", "]",
                "|", "\\",
                ":", ";",
                "\"", "'",
                "<", ">",
                ",", ".", "?", "/"
        );

        int upper = 0;
        int lower = 0;
        int special = 0;

        for(int i=0;i<s.length();i++){
            String s1 = s.charAt(i)+"";
            if(!specialChars.contains(s1) && !Character.isDigit(s.charAt(i)) && s1.toUpperCase().charAt(0) == s.charAt(i)) upper++;
            if(!specialChars.contains(s1) && !Character.isDigit(s.charAt(i)) && s1.toLowerCase().charAt(0) == s.charAt(i)) lower++;
            if(specialChars.contains(s1)) special++;

        }
        System.out.println(upper+" "+lower+" "+special);
        if(special == 0 || upper == 0 || lower == 0) return false;
        return true;
    }
}
