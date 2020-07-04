package com.spiashko.blazepersistencedemo.rsql;

import com.spiashko.blazepersistencedemo.filterenum.FilterAttributesProvider;
import cz.jirutka.rsql.parser.ast.AndNode;
import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.OrNode;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;
import org.springframework.data.jpa.domain.Specification;

public class RsqlSpecVisitor implements RSQLVisitor<Specification<?>, FilterAttributesProvider<?>> {

    private final GenericRsqlSpecBuilder builder;

    public RsqlSpecVisitor() {
        this.builder = new GenericRsqlSpecBuilder();
    }

    public RsqlSpecVisitor(RsqlOperationSpecRegistry registry) {
        this.builder = new GenericRsqlSpecBuilder(registry);
    }

    @Override
    public Specification<?> visit(final AndNode node, final FilterAttributesProvider<?> params) {
        return builder.createSpecification(node, params);
    }

    @Override
    public Specification<?> visit(final OrNode node, final FilterAttributesProvider<?> params) {
        return builder.createSpecification(node, params);
    }

    @Override
    public Specification<?> visit(final ComparisonNode node, final FilterAttributesProvider<?> params) {
        return builder.createSpecification(node, params);
    }

}
