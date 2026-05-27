package com.nithin.DI.DependencyInjection;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "syrup.type",havingValue = "choco")
public class ChocolateSyrup implements Syrup{
    @Override
    public String getSyrupType() {
        return "Chocolate syrup";
    }
}
