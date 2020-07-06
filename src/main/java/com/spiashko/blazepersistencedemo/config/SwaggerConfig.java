package com.spiashko.blazepersistencedemo.config;

import org.springdoc.core.SpringDocUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.domain.Specification;

@Configuration
public class SwaggerConfig {

    static {
        SpringDocUtils.getConfig().addRequestWrapperToIgnore(Specification.class);
    }

}
