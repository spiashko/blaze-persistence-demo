package com.spiashko.blazepersistencedemo.view.person.retrieve;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.spiashko.blazepersistencedemo.model.Person;


@EntityView(Person.class)
public interface PersonIdView {

    @IdMapping
    Long getId();

}
