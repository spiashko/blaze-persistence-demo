package com.spiashko.blazepersistencedemo.rsql;

import com.spiashko.blazepersistencedemo.filterenum.FilterAttributesProvider;
import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.criteria.internal.path.PluralAttributePath;
import org.hibernate.query.criteria.internal.path.SingularAttributePath;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.*;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GenericRsqlSpecification<T> implements Specification<T> {

    private final RsqlOperationSpecRegistry registry;
    private final FilterAttributesProvider<T> attributesProvider;

    private final String property;
    private final ComparisonOperator operator;
    private final List<String> arguments;

    @Override
    public Predicate toPredicate(final Root<T> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {
        Path<String> propertyExpression = parseProperty(root);
        final List<Object> args = castArguments(propertyExpression);
        return registry.getSpec(operator).toPredicate(builder, propertyExpression, args);
    }

    //TODO review as it is done in spring data Sort and how it was done in CXF implementation
    private Path<String> parseProperty(Root<T> root) {
        Path<String> path;

        //TODO use attributesProvider to verify propertie

        if (property.contains(".")) {
            // Nested properties
            String[] pathSteps = property.split("\\.");
            String step = pathSteps[0];
            path = root.get(step);
            From lastFrom = root;

            for (int i = 1; i <= pathSteps.length - 1; i++) {
                if (path instanceof PluralAttributePath) {
                    PluralAttribute attr = ((PluralAttributePath) path).getAttribute();
                    Join join = getJoin(attr, lastFrom);
                    path = join.get(pathSteps[i]);
                    lastFrom = join;
                } else if (path instanceof SingularAttributePath) {
                    SingularAttribute attr = ((SingularAttributePath) path).getAttribute();
                    if (attr.getPersistentAttributeType() != Attribute.PersistentAttributeType.BASIC) {
                        Join join = lastFrom.join(attr, JoinType.LEFT);
                        path = join.get(pathSteps[i]);
                        lastFrom = join;
                    } else {
                        path = path.get(pathSteps[i]);
                    }
                } else {
                    path = path.get(pathSteps[i]);
                }
            }
        } else {
            path = root.get(property);
        }
        return path;
    }

    private Join getJoin(PluralAttribute attr, From from) {
        switch (attr.getCollectionType()) {
            case COLLECTION:
                return from.join((CollectionAttribute) attr);
            case SET:
                return from.join((SetAttribute) attr);
            case LIST:
                return from.join((ListAttribute) attr);
            case MAP:
                return from.join((MapAttribute) attr);
            default:
                return null;
        }
    }

    //TODO check dates
    private List<Object> castArguments(Path<?> propertyExpression) {

        final Class<?> type = propertyExpression.getJavaType();

        final List<Object> args = arguments.stream()
                .map(arg -> {
                    if (type.equals(Integer.class)) {
                        return Integer.parseInt(arg);
                    } else if (type.equals(Long.class)) {
                        return Long.parseLong(arg);
                    } else {
                        return arg;
                    }
                })
                .collect(Collectors.toList());

        return args;
    }

}
