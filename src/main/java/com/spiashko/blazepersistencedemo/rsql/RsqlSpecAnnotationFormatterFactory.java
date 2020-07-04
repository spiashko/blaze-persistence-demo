package com.spiashko.blazepersistencedemo.rsql;

import com.spiashko.blazepersistencedemo.filterenum.FilterAttributesProvider;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@RequiredArgsConstructor
public class RsqlSpecAnnotationFormatterFactory implements AnnotationFormatterFactory<RsqlSpec> {

    private final RSQLParser rsqlParser;
    private final RSQLVisitor<Specification<?>, FilterAttributesProvider<?>> visitor;

    @Override
    public Set<Class<?>> getFieldTypes() {
        Set<Class<?>> fieldTypes = new HashSet<>();
        fieldTypes.add(Specification.class);
        return Collections.unmodifiableSet(fieldTypes);
    }

    @Override
    public Printer<?> getPrinter(RsqlSpec annotation, Class<?> fieldType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Parser<Specification<?>> getParser(RsqlSpec annotation, Class<?> fieldType) {
        return (String text, Locale locale) -> {
            Node rootNode = rsqlParser.parse(text);

            Class<? extends FilterAttributesProvider<?>> providerClass = annotation.value();
            FilterAttributesProvider<?> filterAttributesProvider;
            try {
                filterAttributesProvider = providerClass.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }

            Specification<?> spec = rootNode.accept(visitor, filterAttributesProvider);
            return spec;
        };
    }
}