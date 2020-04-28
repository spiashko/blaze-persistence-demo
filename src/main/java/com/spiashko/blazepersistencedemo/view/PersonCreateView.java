package com.spiashko.blazepersistencedemo.view;

import com.blazebit.persistence.view.CreatableEntityView;
import com.blazebit.persistence.view.EntityView;
import com.spiashko.blazepersistencedemo.model.Person;

@CreatableEntityView
@EntityView(Person.class)
public interface PersonCreateView extends PersonIdView {

    String getName();

    void setName(String name);

}
