package com.spiashko.blazepersistencedemo.swagger;

import lombok.Getter;
import org.springdoc.core.customizers.OpenApiCustomiser;

import java.util.ArrayList;
import java.util.List;

@Getter
public class RsqlSpecOpenApiCustomiserRegistry {

    private final List<OpenApiCustomiser> customisers = new ArrayList<>();

    public void addCustomiser(OpenApiCustomiser customiser){
        customisers.add(customiser);
    }

}
