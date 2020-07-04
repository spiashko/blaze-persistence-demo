package com.spiashko.blazepersistencedemo.config;

import com.spiashko.blazepersistencedemo.rsql.RsqlSpecAnnotationFormatterFactory;
import com.spiashko.blazepersistencedemo.rsql.RsqlSpecVisitor;
import cz.jirutka.rsql.parser.RSQLParser;
import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatterForFieldAnnotation(new RsqlSpecAnnotationFormatterFactory(
                new RSQLParser(),
                new RsqlSpecVisitor()
        ));
    }

//    @Override
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//        argumentResolvers.add(new SpecificationArgumentResolver());
//    }
}