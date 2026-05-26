package com.nithin.mudule1Introduction.IntroToSpring.impl;


import com.nithin.mudule1Introduction.IntroToSpring.NotificationService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;


@Component
@Qualifier(value = "emailNotif")
//@ConditionalOnProperty(name = "notification.type",havingValue = "email")
public class EmailNotificationService implements NotificationService {
    @Override
    public void send(String message) {
        System.out.println("Email notification sent"+message);
    }
}
