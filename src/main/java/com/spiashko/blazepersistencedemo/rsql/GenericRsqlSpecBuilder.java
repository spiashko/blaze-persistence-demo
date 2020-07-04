package com.spiashko.blazepersistencedemo.rsql;

import com.spiashko.blazepersistencedemo.filterenum.FilterAttributesProvider;
import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.LogicalNode;
import cz.jirutka.rsql.parser.ast.LogicalOperator;
import cz.jirutka.rsql.parser.ast.Node;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GenericRsqlSpecBuilder {

    private final RsqlOperationSpecRegistry registry;

    public GenericRsqlSpecBuilder() {
        this.registry = new RsqlOperationSpecRegistry();
    }

    public Specification<?> createSpecification(final Node node,
                                                FilterAttributesProvider<?> attributesProvider) {
        if (node instanceof LogicalNode) {
            return createSpecification((LogicalNode) node, attributesProvider);
        }
        if (node instanceof ComparisonNode) {
            return createSpecification((ComparisonNode) node, attributesProvider);
        }
        return null;
    }

    public Specification<?> createSpecification(final LogicalNode logicalNode,
                                                FilterAttributesProvider<?> attributesProvider) {

        List<Specification> specs = logicalNode.getChildren()
                .stream()
                .map(node -> createSpecification(node, attributesProvider))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        //TODO rewrite for streams
        Specification result = specs.get(0);
        if (logicalNode.getOperator() == LogicalOperator.AND) {
            for (int i = 1; i < specs.size(); i++) {
                result = Specification.where(result).and(specs.get(i));
            }
        } else if (logicalNode.getOperator() == LogicalOperator.OR) {
            for (int i = 1; i < specs.size(); i++) {
                result = Specification.where(result).or(specs.get(i));
            }
        }

        return result;
    }

    public Specification<?> createSpecification(final ComparisonNode comparisonNode,
                                                FilterAttributesProvider<?> attributesProvider) {

        return new GenericRsqlSpecification<>(
                registry,
                attributesProvider,
                comparisonNode.getSelector(),
                comparisonNode.getOperator(),
                comparisonNode.getArguments());
    }

}
