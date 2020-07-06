package com.spiashko.blazepersistencedemo.config;

import com.spiashko.blazepersistencedemo.swagger.RsqlSpecOpenApiCustomiserRegistry;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.SpringDocUtils;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.domain.Specification;

@Configuration
public class SwaggerConfig {

    static {
        SpringDocUtils.getConfig().addRequestWrapperToIgnore(Specification.class);
    }

    @Bean
    public RsqlSpecOpenApiCustomiserRegistry openApiCustomiserRegistry() {
        return new RsqlSpecOpenApiCustomiserRegistry();
    }

    @Bean
    public OpenApiCustomiser myOpenApiCustomiser(RsqlSpecOpenApiCustomiserRegistry openApiCustomiserRegistry) {
        return (OpenAPI openApi) ->
                openApiCustomiserRegistry.getCustomisers().forEach(customiser -> customiser.customise(openApi));
    }

}
