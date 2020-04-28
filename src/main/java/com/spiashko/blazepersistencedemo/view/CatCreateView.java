package com.spiashko.blazepersistencedemo.view;

import com.blazebit.persistence.view.CreatableEntityView;
import com.blazebit.persistence.view.EntityView;
import com.spiashko.blazepersistencedemo.model.Cat;


@CreatableEntityView
@EntityView(Cat.class)
public interface CatCreateView extends CatUpdateView {

    PersonIdView getOwner();

    void setOwner(PersonIdView owner);
}
