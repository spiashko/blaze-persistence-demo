package com.spiashko.blazepersistencedemo.repository;

import com.spiashko.blazepersistencedemo.model.Cat;
import com.spiashko.blazepersistencedemo.view.cat.managment.CatCreateView;
import com.spiashko.blazepersistencedemo.view.cat.managment.CatUpdateView;

import java.util.List;

public interface CatRepository extends BaseRepo<Cat> {

    <T> List<T> findAll(Class<T> clazz);

    <T> T findById(Long id, Class<T> clazz);

    CatCreateView save(CatCreateView createView);

    CatUpdateView save(CatUpdateView createView);
}
