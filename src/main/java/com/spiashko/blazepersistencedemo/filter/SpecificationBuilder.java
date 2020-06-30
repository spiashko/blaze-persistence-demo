package com.spiashko.blazepersistencedemo.filter;

import com.blazebit.text.ParserContext;
import com.blazebit.text.SerializableFormat;
import com.spiashko.blazepersistencedemo.filter.Filter;
import com.spiashko.blazepersistencedemo.filterenum.FilterAttributesProvider;
import net.jodah.typetools.TypeResolver;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class SpecificationBuilder {

    public <T> Specification<T> build(final Filter[] filter, FilterAttributesProvider<T> provider){
        return build(filter, provider.getFilterAttributes());
    }

    public <T> Specification<T> build(final Filter[] filter, Map<String, SerializableFormat<?>> filterAttributes) {
        if (filter == null || filter.length == 0) {
            return (Specification<T>) (root, cq, cb) -> cb.and();
        }
        return (Specification<T>) (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            ParserContext parserContext = new ParserContextImpl();
            for (Filter f : filter) {
                SerializableFormat<?> format = filterAttributes.get(f.getField());

                if (format == null) {
                    throw new IllegalArgumentException("bad filed name is supplied");
                }

                Supplier<Serializable> value = () -> getParsed(parserContext, f.getValue(), format);
                Supplier<Comparable> valueC = () -> (Comparable) getParsed(parserContext, f.getValue(), format);
                Supplier<List<Serializable>> values = () -> f.getValues().stream()
                        .map(v -> getParsed(parserContext, v, format))
                        .collect(Collectors.toList());
                Supplier<Comparable> lowValue = () -> (Comparable) getParsed(parserContext, f.getLow(), format);
                Supplier<Comparable> highValue = () -> (Comparable) getParsed(parserContext, f.getHigh(), format);

                Path<?> path = getPath(root, f);

                Class<?>[] typeArguments = TypeResolver.resolveRawArguments(SerializableFormat.class, format.getClass());
                if (!path.getJavaType().equals(typeArguments[0])) {
                    throw new RuntimeException("formatter type and type of field didn't match");
                }

                Supplier<Expression<Comparable>> pathEC = () -> (Expression<Comparable>) path;
                Supplier<Expression<String>> pathES = () -> (Expression<String>) path;

                Predicate p = switch (f.getKind()) {
                    case EQ -> cb.equal(path, value.get());
                    case GT -> cb.greaterThan(pathEC.get(), valueC.get());
                    case LT -> cb.lessThan(pathEC.get(), valueC.get());
                    case GTE -> cb.greaterThanOrEqualTo(pathEC.get(), valueC.get());
                    case LTE -> cb.lessThanOrEqualTo(pathEC.get(), valueC.get());
                    case IN -> path.in(values.get());
                    case BETWEEN -> cb.between(pathEC.get(), lowValue.get(), highValue.get());
                    case STARTS_WITH -> cb.like(pathES.get(), value.get() + "%");
                    case ENDS_WITH -> cb.like(pathES.get(), "%" + value.get());
                    case CONTAINS -> cb.like(pathES.get(), "%" + value.get() + "%");
                };

                predicates.add(p);
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private <T> Path<?> getPath(Root<T> root, Filter f) {
        String[] fieldParts = f.getField().split("\\.");
        Path<?> path = root.get(fieldParts[0]);
        for (int i = 1; i < fieldParts.length; i++) {
            path = path.get(fieldParts[i]);
        }
        return path;
    }


    private Serializable getParsed(ParserContext parserContext, String value, SerializableFormat<?> format) {
        try {
            return format.parse(value, parserContext);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static class ParserContextImpl implements ParserContext {
        private final Map<String, Object> contextMap = new HashMap<>();

        public Object getAttribute(String name) {
            return this.contextMap.get(name);
        }

        public void setAttribute(String name, Object value) {
            this.contextMap.put(name, value);
        }
    }

}
