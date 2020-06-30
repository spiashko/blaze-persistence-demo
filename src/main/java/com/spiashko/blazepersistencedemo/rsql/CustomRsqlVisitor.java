package com.spiashko.blazepersistencedemo.rsql;

import com.spiashko.blazepersistencedemo.filterenum.FilterAttributesProvider;
import cz.jirutka.rsql.parser.ast.AndNode;
import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.OrNode;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;
import org.springframework.data.jpa.domain.Specification;

public class CustomRsqlVisitor<T> implements RSQLVisitor<Specification<T>, FilterAttributesProvider<T>> {

    private GenericRsqlSpecBuilder<T> builder;

    public CustomRsqlVisitor() {
        builder = new GenericRsqlSpecBuilder<T>();
    }

    @Override
    public Specification<T> visit(final AndNode node, final FilterAttributesProvider<T> param) {
        return builder.createSpecification(node);
    }

    @Override
    public Specification<T> visit(final OrNode node, final FilterAttributesProvider<T> param) {
        return builder.createSpecification(node);
    }

    @Override
    public Specification<T> visit(final ComparisonNode node, final FilterAttributesProvider<T> params) {
        return builder.createSpecification(node);
    }

}
