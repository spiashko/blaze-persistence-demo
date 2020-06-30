package com.spiashko.blazepersistencedemo.rsql;

import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import cz.jirutka.rsql.parser.ast.RSQLOperators;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class RsqlOperationPredicateBuilders {

    public static final RsqlOperationPredicateBuilder EQUAL =
            new RsqlOperationPredicateBuilder() {

                @Override
                public Predicate toPredicate(CriteriaBuilder builder, Path<String> propertyExpression, List<Object> args) {
                    final Object argument = args.get(0);
                    if (argument instanceof String) {
                        return builder.like(propertyExpression, ((String) argument).replace('*', '%'));
                    } else if (argument == null) {
                        return builder.isNull(propertyExpression);
                    } else {
                        return builder.equal(propertyExpression, argument);
                    }
                }

                @Override
                public boolean support(ComparisonOperator comparisonOperator) {
                    return RSQLOperators.EQUAL.equals(comparisonOperator);
                }
            };

    public static final RsqlOperationPredicateBuilder NOT_EQUAL =
            new RsqlOperationPredicateBuilder() {

                @Override
                public Predicate toPredicate(CriteriaBuilder builder, Path<String> propertyExpression, List<Object> args) {
                    final Object argument = args.get(0);
                    if (argument instanceof String) {
                        return builder.notLike(propertyExpression, ((String) argument).replace('*', '%'));
                    } else if (argument == null) {
                        return builder.isNotNull(propertyExpression);
                    } else {
                        return builder.notEqual(propertyExpression, argument);
                    }
                }

                @Override
                public boolean support(ComparisonOperator comparisonOperator) {
                    return RSQLOperators.NOT_EQUAL.equals(comparisonOperator);
                }
            };

    public static final RsqlOperationPredicateBuilder GREATER_THAN =
            new RsqlOperationPredicateBuilder() {

                @Override
                public Predicate toPredicate(CriteriaBuilder builder, Path<String> propertyExpression, List<Object> args) {
                    final Object argument = args.get(0);
                    return builder.greaterThan(propertyExpression, argument.toString());
                }

                @Override
                public boolean support(ComparisonOperator comparisonOperator) {
                    return RSQLOperators.GREATER_THAN.equals(comparisonOperator);
                }
            };

    public static final RsqlOperationPredicateBuilder GREATER_THAN_OR_EQUAL =
            new RsqlOperationPredicateBuilder() {

                @Override
                public Predicate toPredicate(CriteriaBuilder builder, Path<String> propertyExpression, List<Object> args) {
                    final Object argument = args.get(0);
                    return builder.greaterThanOrEqualTo(propertyExpression, argument.toString());
                }

                @Override
                public boolean support(ComparisonOperator comparisonOperator) {
                    return RSQLOperators.GREATER_THAN_OR_EQUAL.equals(comparisonOperator);
                }
            };

    public static final RsqlOperationPredicateBuilder LESS_THAN =
            new RsqlOperationPredicateBuilder() {

                @Override
                public Predicate toPredicate(CriteriaBuilder builder, Path<String> propertyExpression, List<Object> args) {
                    final Object argument = args.get(0);
                    return builder.lessThan(propertyExpression, argument.toString());
                }

                @Override
                public boolean support(ComparisonOperator comparisonOperator) {
                    return RSQLOperators.LESS_THAN.equals(comparisonOperator);
                }
            };

    public static final RsqlOperationPredicateBuilder LESS_THAN_OR_EQUAL =
            new RsqlOperationPredicateBuilder() {

                @Override
                public Predicate toPredicate(CriteriaBuilder builder, Path<String> propertyExpression, List<Object> args) {
                    final Object argument = args.get(0);
                    return builder.lessThanOrEqualTo(propertyExpression, argument.toString());
                }

                @Override
                public boolean support(ComparisonOperator comparisonOperator) {
                    return RSQLOperators.LESS_THAN_OR_EQUAL.equals(comparisonOperator);
                }
            };

    public static final RsqlOperationPredicateBuilder IN =
            new RsqlOperationPredicateBuilder() {

                @Override
                public Predicate toPredicate(CriteriaBuilder builder, Path<String> propertyExpression, List<Object> args) {
                    return propertyExpression.in(args);
                }

                @Override
                public boolean support(ComparisonOperator comparisonOperator) {
                    return RSQLOperators.IN.equals(comparisonOperator);
                }
            };

    public static final RsqlOperationPredicateBuilder NOT_IN =
            new RsqlOperationPredicateBuilder() {

                @Override
                public Predicate toPredicate(CriteriaBuilder builder, Path<String> propertyExpression, List<Object> args) {
                    return builder.not(propertyExpression.in(args));
                }

                @Override
                public boolean support(ComparisonOperator comparisonOperator) {
                    return RSQLOperators.NOT_IN.equals(comparisonOperator);
                }
            };


    public static Set<RsqlOperationPredicateBuilder> defaultBuilders() {
        return new HashSet<>(Arrays.asList(EQUAL, NOT_EQUAL, GREATER_THAN, GREATER_THAN_OR_EQUAL,
                LESS_THAN, LESS_THAN_OR_EQUAL, IN, NOT_IN));
    }

}
