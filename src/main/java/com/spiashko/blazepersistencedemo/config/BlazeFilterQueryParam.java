package com.spiashko.blazepersistencedemo.config;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.Explode;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Parameter(in = ParameterIn.QUERY
        , name = "filter"
        , content = @Content(array = @ArraySchema(schema = @Schema(type = "string")))
        , example = "firstName==john;lastName==doe"
        , style = ParameterStyle.FORM
        , explode = Explode.TRUE
)
public @interface
BlazeFilterQueryParam {

    @AliasFor(annotation = Parameter.class)
    String description() default "Filter criteria in object format. "
            + "Multiple filter criteria are supported.";

}