package com.spiashko.blazepersistencedemo.repository;

import com.spiashko.blazepersistencedemo.model.Cat;
import com.spiashko.blazepersistencedemo.view.cat.managment.CatCreateView;
import com.spiashko.blazepersistencedemo.view.cat.managment.CatUpdateView;

public interface CatRepository extends BaseRepo<Cat> {

    CatCreateView save(CatCreateView createView);

    CatUpdateView save(CatUpdateView createView);
}
