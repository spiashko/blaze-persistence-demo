package com.spiashko.blazepersistencedemo.view.cat.retrieve;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.spiashko.blazepersistencedemo.model.Cat;

@EntityView(Cat.class)
public interface CatIdView {

    @IdMapping
    Long getId();

}
