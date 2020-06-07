package com.spiashko.blazepersistencedemo.view.cat.managment;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.UpdatableEntityView;
import com.spiashko.blazepersistencedemo.model.Cat;


@UpdatableEntityView
@EntityView(Cat.class)
public abstract class CatUpdateView extends CatSaveView {
}
