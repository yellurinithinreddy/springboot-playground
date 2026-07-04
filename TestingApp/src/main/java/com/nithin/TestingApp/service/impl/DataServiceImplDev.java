package com.nithin.TestingApp.service.impl;

import com.nithin.TestingApp.service.DataService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"dev","default"})
public class DataServiceImplDev implements DataService {
    @Override
    public String getData() {
        return "Dev Data";
    }
}
