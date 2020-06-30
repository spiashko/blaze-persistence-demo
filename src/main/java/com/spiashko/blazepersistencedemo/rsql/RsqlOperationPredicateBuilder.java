package com.spiashko.blazepersistencedemo.rsql;

import cz.jirutka.rsql.parser.ast.ComparisonOperator;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.List;

public interface RsqlOperationPredicateBuilder {

    Predicate toPredicate(CriteriaBuilder criteriaBuilder, Path<String> propertyExpression, List<Object> args);

    boolean support(ComparisonOperator comparisonOperator);

}
