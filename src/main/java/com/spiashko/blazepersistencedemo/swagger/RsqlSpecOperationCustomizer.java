package com.spiashko.blazepersistencedemo.swagger;

import com.spiashko.blazepersistencedemo.rsql.RsqlSpec;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.core.MethodParameter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class RsqlSpecOperationCustomizer implements OperationCustomizer {
    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {

        List<MethodParameter> specParams = Arrays.stream(handlerMethod.getMethodParameters())
                .filter(methodParameter -> Specification.class.equals(methodParameter.getParameterType()) &&
                        methodParameter.hasParameterAnnotation(RsqlSpec.class))
                .collect(Collectors.toList());

        if (specParams.isEmpty()) {
            return operation;
        }

        specParams.forEach((param) -> enrichOperationWithSpecParam(operation, param));

        return operation;
    }

    private void enrichOperationWithSpecParam(Operation operation, MethodParameter parameter) {

        /**
         * @Parameter(in = ParameterIn.QUERY
         *         , name = "filter"
         *         , description = "Filter criteria in RSQL/FIQL format."
         *         , content = @Content(schema = @Schema(type = "string"))
         *         , example = "firstName==john;lastName==doe"
         *         , style = ParameterStyle.FORM
         *         , explode = Explode.TRUE
         * )
         */

        RsqlSpec rsqlSpecAnnotation = Objects.requireNonNull(parameter.getParameterAnnotation(RsqlSpec.class));

        Parameter parameterHeader = new Parameter()
                .in(ParameterIn.QUERY.toString())
                .name(rsqlSpecAnnotation.requestParamName())
                .description("Filter criteria in RSQL/FIQL format.")
                .schema(new StringSchema().addEnumItem("v1")._default("v1").name("Accept-version"))
                .description("Custom Header To be Pass");

        operation.addParametersItem(parameterHeader);
    }
}