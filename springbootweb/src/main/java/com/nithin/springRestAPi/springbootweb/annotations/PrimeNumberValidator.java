package com.nithin.springRestAPi.springbootweb.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PrimeNumberValidator implements ConstraintValidator<PrimeNumber,Integer> {
    @Override
    public boolean isValid(Integer num, ConstraintValidatorContext constraintValidatorContext) {
        return isPrime(num);
    }

    private boolean isPrime(Integer num) {
        for(int i=2;i < num ;i++){
            if(num%i == 0) return false;
        }
        return true;
    }
}
