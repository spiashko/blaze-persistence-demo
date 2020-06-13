package com.spiashko.blazepersistencedemo.repository;

import com.blazebit.persistence.spring.data.repository.KeysetAwarePage;
import com.spiashko.blazepersistencedemo.view.cat.retrieve.CatSimpleView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.Repository;

public interface CatPageRepository extends Repository<CatSimpleView, Long> {

    KeysetAwarePage<CatSimpleView> findAll(Specification<CatSimpleView> specification, Pageable pageable);

}
