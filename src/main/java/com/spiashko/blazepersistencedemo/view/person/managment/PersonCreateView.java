package com.spiashko.blazepersistencedemo.view.person.managment;

import com.blazebit.persistence.view.CreatableEntityView;
import com.blazebit.persistence.view.EntityView;
import com.spiashko.blazepersistencedemo.model.Person;

@CreatableEntityView
@EntityView(Person.class)
public interface PersonCreateView extends PersonSaveView {

}
