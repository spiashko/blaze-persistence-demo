package com.spiashko.blazepersistencedemo.view;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.UpdatableEntityView;
import com.spiashko.blazepersistencedemo.model.Cat;


@UpdatableEntityView
@EntityView(Cat.class)
public interface CatUpdateView extends CatSaveView {
}
