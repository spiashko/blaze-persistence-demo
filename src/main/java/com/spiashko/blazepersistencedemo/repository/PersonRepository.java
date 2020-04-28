package com.spiashko.blazepersistencedemo.repository;

import com.spiashko.blazepersistencedemo.model.Person;
import com.spiashko.blazepersistencedemo.view.PersonCreateView;
import com.spiashko.blazepersistencedemo.view.PersonSimpleView;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface PersonRepository extends Repository<Person, Long> {
    List<PersonSimpleView> findAll();

    PersonCreateView save(PersonCreateView createView);
}
