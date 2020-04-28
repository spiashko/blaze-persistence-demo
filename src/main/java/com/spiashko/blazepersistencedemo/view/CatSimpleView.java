package com.spiashko.blazepersistencedemo.view;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.spiashko.blazepersistencedemo.model.Cat;


@EntityView(Cat.class)
public interface CatSimpleView {

    @IdMapping
    Long getId();

    String getName();
}
