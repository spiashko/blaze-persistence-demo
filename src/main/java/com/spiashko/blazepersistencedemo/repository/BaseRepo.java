package com.spiashko.blazepersistencedemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface BaseRepo<T> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {

    <V> List<V> findAll(Class<V> clazz);

    <V> V findById(Long id, Class<V> clazz);

}
