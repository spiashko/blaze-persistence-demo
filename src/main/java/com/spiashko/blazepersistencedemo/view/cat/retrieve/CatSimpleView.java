package com.spiashko.blazepersistencedemo.view.cat.retrieve;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.Mapping;
import com.spiashko.blazepersistencedemo.model.Cat;

import java.time.LocalDate;


@EntityView(Cat.class)
public interface CatSimpleView extends CatIdView {

    String getName();

    LocalDate getDob();

    @Mapping("owner.id")
    Long getOwnerId();
}
