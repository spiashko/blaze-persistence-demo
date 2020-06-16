package com.spiashko.blazepersistencedemo.view.cat.retrieve;

import com.blazebit.persistence.view.EntityView;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spiashko.blazepersistencedemo.model.Cat;
import com.spiashko.blazepersistencedemo.view.person.retrieve.PersonSimpleView;

@JsonIgnoreProperties(CatSimpleView_.OWNER_ID)
@EntityView(Cat.class)
public interface CatWithOwnerView extends CatSimpleView {

    PersonSimpleView getOwner();

}
