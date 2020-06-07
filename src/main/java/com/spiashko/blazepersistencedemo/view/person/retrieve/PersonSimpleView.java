package com.spiashko.blazepersistencedemo.view.person.retrieve;

import com.blazebit.persistence.view.EntityView;
import com.spiashko.blazepersistencedemo.model.Person;


@EntityView(Person.class)
public interface PersonSimpleView extends PersonIdView {

    String getName();

}
