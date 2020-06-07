package com.spiashko.blazepersistencedemo.view.cat.managment;

import com.blazebit.persistence.view.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spiashko.blazepersistencedemo.model.Cat;
import com.spiashko.blazepersistencedemo.view.cat.retrieve.CatSimpleView;
import com.spiashko.blazepersistencedemo.view.person.retrieve.PersonIdView;

import java.time.LocalDate;


@UpdatableEntityView
@EntityView(Cat.class)
public abstract class CatSaveView implements CatSimpleView {

    public abstract void setName(String name);

    public abstract void setDob(LocalDate dob);

    @JsonIgnore
    @Mapping("owner")
    protected abstract PersonIdView getOwnerInternal();

    protected abstract void setOwnerInternal(PersonIdView owner);

    protected abstract EntityViewManager evm();

    public void setOwnerId(Long id) {
        setOwnerInternal(evm().getReference(PersonIdView.class, id));
    }

    public Long getOwnerId(Long id) {
        return getOwnerInternal().getId();
    }

    @JsonIgnore
    @IdMapping
    public abstract Long getId();
}
