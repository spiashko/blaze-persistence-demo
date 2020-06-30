package com.spiashko.blazepersistencedemo.rsql;

import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public class RsqlOperationSpecRegistry {

    private final Set<RsqlOperationPredicateBuilder> builders;

    public RsqlOperationSpecRegistry() {
        builders = RsqlOperationPredicateBuilders.defaultBuilders();
    }

    public RsqlOperationPredicateBuilder getSpec(ComparisonOperator operator) {
        return builders.stream()
                .filter(b -> b.support(operator))
                .reduce((element, otherElement) -> {
                    throw new IllegalArgumentException("only one builder is expected");
                })
                .orElseThrow();
    }

}
