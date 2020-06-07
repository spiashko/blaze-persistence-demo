package com.spiashko.blazepersistencedemo.view.person.managment;

import com.blazebit.persistence.view.CreatableEntityView;
import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spiashko.blazepersistencedemo.model.Person;
import com.spiashko.blazepersistencedemo.view.person.retrieve.PersonSimpleView;

@CreatableEntityView
@EntityView(Person.class)
public interface PersonSaveView extends PersonSimpleView {

    void setName(String name);

    @JsonIgnore
    @IdMapping
    Long getId();

}
