package com.nithin.DI.DependencyInjection;

import org.springframework.stereotype.Component;

@Component
public class CakeBaker {

    private final Frosting frosting;

    private final Syrup syrup;

    public CakeBaker(Frosting frosting, Syrup syrup) {
        this.frosting = frosting;
        this.syrup = syrup;
    }

    void bakeCake(){
        System.out.println("Cake is baked by using "+frosting.getFrostingType()+" and "+syrup.getSyrupType());
    }
}
