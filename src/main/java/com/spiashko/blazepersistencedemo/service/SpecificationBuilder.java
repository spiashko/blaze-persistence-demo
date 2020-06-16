package com.spiashko.blazepersistencedemo.service;

import com.blazebit.text.ParserContext;
import com.blazebit.text.SerializableFormat;
import com.spiashko.blazepersistencedemo.filter.Filter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SpecificationBuilder {

    public <T> Specification<T> getSpecificationForFilter(final Filter[] filter, Map<String, SerializableFormat<?>> filterAttributes) {
        if (filter == null || filter.length == 0) {
            return null;
        }
        return (Specification<T>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            ParserContext parserContext = new ParserContextImpl();
            try {
                for (Filter f : filter) {
                    SerializableFormat<?> format = filterAttributes.get(f.getField());
                    if (format != null) {
                        String[] fieldParts = f.getField().split("\\.");
                        Path<?> path = root.get(fieldParts[0]);
                        for (int i = 1; i < fieldParts.length; i++) {
                            path = path.get(fieldParts[i]);
                        }
                        switch (f.getKind()) {
                            case EQ:
                                predicates.add(criteriaBuilder.equal(path, format.parse(f.getValue(), parserContext)));
                                break;
                            case GT:
                                predicates.add(criteriaBuilder.greaterThan((Expression<Comparable>) path, (Comparable) format.parse(f.getValue(), parserContext)));
                                break;
                            case LT:
                                predicates.add(criteriaBuilder.lessThan((Expression<Comparable>) path, (Comparable) format.parse(f.getValue(), parserContext)));
                                break;
                            case GTE:
                                predicates.add(criteriaBuilder.greaterThanOrEqualTo((Expression<Comparable>) path, (Comparable) format.parse(f.getValue(), parserContext)));
                                break;
                            case LTE:
                                predicates.add(criteriaBuilder.lessThanOrEqualTo((Expression<Comparable>) path, (Comparable) format.parse(f.getValue(), parserContext)));
                                break;
                            case IN:
                                List<String> values = f.getValues();
                                List<Object> filterValues = new ArrayList<>(values.size());
                                for (String value : values) {
                                    filterValues.add(format.parse(value, parserContext));
                                }
                                predicates.add(path.in(filterValues));
                                break;
                            case BETWEEN:
                                predicates.add(criteriaBuilder.between((Expression<Comparable>) path, (Comparable) format.parse(f.getLow(), parserContext), (Comparable) format.parse(f.getHigh(), parserContext)));
                                break;
                            case STARTS_WITH:
                                predicates.add(criteriaBuilder.like((Expression<String>) path, format.parse(f.getValue(), parserContext) + "%"));
                                break;
                            case ENDS_WITH:
                                predicates.add(criteriaBuilder.like((Expression<String>) path, "%" + format.parse(f.getValue(), parserContext)));
                                break;
                            case CONTAINS:
                                predicates.add(criteriaBuilder.like((Expression<String>) path, "%" + format.parse(f.getValue(), parserContext) + "%"));
                                break;
                            default:
                                throw new UnsupportedOperationException("Unsupported kind: " + f.getKind());
                        }
                    }
                }
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
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
