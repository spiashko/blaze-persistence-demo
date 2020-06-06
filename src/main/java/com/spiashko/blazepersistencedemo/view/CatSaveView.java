package com.spiashko.blazepersistencedemo.view;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.UpdatableEntityView;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spiashko.blazepersistencedemo.model.Cat;


@UpdatableEntityView
@EntityView(Cat.class)
public interface CatSaveView extends CatSimpleView {

    void setName(String name);

    Integer getAge();

    void setAge(Integer age);

    @JsonIgnore
    @IdMapping
    Long getId();
}
