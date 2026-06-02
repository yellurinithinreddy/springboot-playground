package com.nithin.springRestAPi.springbootweb.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = {PrimeNumberValidator.class}
)
public @interface PrimeNumber {

    String message() default "please enter a prime number only";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
