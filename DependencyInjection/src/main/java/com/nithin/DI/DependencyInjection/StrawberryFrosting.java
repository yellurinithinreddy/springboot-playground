package com.nithin.DI.DependencyInjection;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "frosting.type",havingValue = "straw")
public class StrawberryFrosting implements Frosting{
    @Override
    public String getFrostingType() {
        return "Strawberry frosting";
    }
}
