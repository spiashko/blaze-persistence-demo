package com.spiashko.blazepersistencedemo.config;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.Explode;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Parameter(in = ParameterIn.QUERY
        , description = "Zero-based page index (0..N)"
        , name = "page"
        , content = @Content(schema = @Schema(type = "integer", defaultValue = "0")))
@Parameter(in = ParameterIn.QUERY
        , description = "The size of the page to be returned"
        , name = "size"
        , content = @Content(schema = @Schema(type = "integer", defaultValue = "5")))
@Parameter(in = ParameterIn.QUERY
        , description = "Sorting criteria in the format: property(,asc|desc). "
        + "Default sort order is ascending. " + "Multiple sort criteria are supported."
        , name = "sort"
        , content = @Content(array = @ArraySchema(schema = @Schema(type = "string", example = "id,desc")))
        , example = "[\"name,desc\",\"id,desc\"]"
        , style = ParameterStyle.FORM
        , explode = Explode.TRUE)
@Parameter(in = ParameterIn.QUERY
        , description = "Zero-based previous page index (0..N)"
        , name = "prevPage"
        , content = @Content(schema = @Schema(type = "integer", defaultValue = "0")))
@Parameter(in = ParameterIn.QUERY
        , description = "lowest value from previous page but only with fields from sort"
        , name = "lowest"
        , content = @Content(schema = @Schema(type = "string", example = "{\"name\":\"abc\",\"id\":5}")))
@Parameter(in = ParameterIn.QUERY
        , description = "highest value from previous page but only with fields from sort"
        , name = "highest"
        , content = @Content(schema = @Schema(type = "string", example = "{\"name\":\"cba\",\"id\":10}")))
public @interface
BlazePageableAsQueryParam {

}