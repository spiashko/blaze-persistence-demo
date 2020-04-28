package com.spiashko.blazepersistencedemo.view;

import com.blazebit.persistence.view.EntityView;
import com.spiashko.blazepersistencedemo.model.Cat;


@EntityView(Cat.class)
public interface CatWithOwnerView extends CatSimpleView {

    PersonSimpleView getOwner();

}
