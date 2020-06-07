package com.spiashko.blazepersistencedemo.repository;

import com.spiashko.blazepersistencedemo.model.Person;
import com.spiashko.blazepersistencedemo.view.person.managment.PersonCreateView;

public interface PersonRepository extends BaseRepo<Person> {

    PersonCreateView save(PersonCreateView createView);
}
