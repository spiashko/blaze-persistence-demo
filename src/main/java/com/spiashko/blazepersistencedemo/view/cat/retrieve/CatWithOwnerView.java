package com.spiashko.blazepersistencedemo.view.cat.retrieve;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.Mapping;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spiashko.blazepersistencedemo.model.Cat;
import com.spiashko.blazepersistencedemo.view.person.retrieve.PersonSimpleView;


@EntityView(Cat.class)
public interface CatWithOwnerView extends CatSimpleView {

    PersonSimpleView getOwner();

    @JsonIgnore
    @Mapping("owner.id")
    Long getOwnerId();

}
