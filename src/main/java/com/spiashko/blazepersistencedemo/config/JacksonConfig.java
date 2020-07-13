package com.spiashko.blazepersistencedemo.config;

import com.blazebit.persistence.spring.data.repository.KeysetAwarePage;
import com.spiashko.blazepersistencedemo.jackson.KeysetAwarePageMixin;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizerToAddKeysetAwarePageMixin() {
        return builder -> builder.mixIn(KeysetAwarePage.class, KeysetAwarePageMixin.class);
    }

}
