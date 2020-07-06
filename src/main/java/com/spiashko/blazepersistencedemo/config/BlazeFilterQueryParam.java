package com.spiashko.blazepersistencedemo.config;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.Explode;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Parameter(in = ParameterIn.QUERY
        , name = "filter"
        , description = "Filter criteria in RSQL/FIQL format."
        , content = @Content(schema = @Schema(type = "string"))
        , example = "firstName==john;lastName==doe"
        , style = ParameterStyle.FORM
        , explode = Explode.TRUE
//        , extensions = @Extension(properties = @ExtensionProperty(
//                name = "x-lol", value = "[\"name\",\"id\"]", parseValue = true))
)
public @interface
BlazeFilterQueryParam {

}