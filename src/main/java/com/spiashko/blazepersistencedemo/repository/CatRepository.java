package com.spiashko.blazepersistencedemo.repository;

import com.spiashko.blazepersistencedemo.model.Cat;
import com.spiashko.blazepersistencedemo.view.CatCreateView;
import com.spiashko.blazepersistencedemo.view.CatSimpleView;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface CatRepository extends Repository<Cat, Long> {

    List<CatSimpleView> findAll();

    CatCreateView save(CatCreateView createView);
}
