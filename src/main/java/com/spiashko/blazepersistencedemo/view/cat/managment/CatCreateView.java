package com.spiashko.blazepersistencedemo.view.cat.managment;

import com.blazebit.persistence.view.CreatableEntityView;
import com.blazebit.persistence.view.EntityView;
import com.spiashko.blazepersistencedemo.model.Cat;


@CreatableEntityView
@EntityView(Cat.class)
public abstract class CatCreateView extends CatSaveView {

}
