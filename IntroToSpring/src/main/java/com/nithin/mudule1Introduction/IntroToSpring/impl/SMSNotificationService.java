package com.nithin.mudule1Introduction.IntroToSpring.impl;

import com.nithin.mudule1Introduction.IntroToSpring.NotificationService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
@Qualifier(value = "smsNotif")
@ConditionalOnProperty(name = "notification.type",havingValue = "sms")
public class SMSNotificationService implements NotificationService {
    @Override
    public void send(String message) {
        System.out.println("sms notification sent "+message);
    }
}
