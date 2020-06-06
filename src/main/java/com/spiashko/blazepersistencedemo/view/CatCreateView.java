package com.spiashko.blazepersistencedemo.view;

import com.blazebit.persistence.view.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spiashko.blazepersistencedemo.model.Cat;


@CreatableEntityView
@EntityView(Cat.class)
public abstract class CatCreateView implements CatSaveView {

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
}
