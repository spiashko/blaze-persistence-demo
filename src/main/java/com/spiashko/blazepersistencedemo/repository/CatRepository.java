package com.spiashko.blazepersistencedemo.repository;

import com.blazebit.persistence.view.EntityViewSetting;
import com.spiashko.blazepersistencedemo.model.Cat;
import com.spiashko.blazepersistencedemo.view.CatCreateView;
import com.spiashko.blazepersistencedemo.view.CatSimpleView;
import com.spiashko.blazepersistencedemo.view.CatWithOwnerView;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface CatRepository extends Repository<Cat, Long> {

    List<CatSimpleView> findAllSimple();

    List<CatWithOwnerView> findAllWithOwner();

    <T> List<T> findAll(EntityViewSetting settings);

    CatCreateView save(CatCreateView createView);
}
