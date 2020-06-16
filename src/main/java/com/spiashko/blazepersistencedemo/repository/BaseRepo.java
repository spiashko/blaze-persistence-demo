package com.spiashko.blazepersistencedemo.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface BaseRepo<T> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {

    <V> List<V> findAll(Class<V> clazz);

    <V> List<V> findAll(Class<V> clazz, Specification<V> spec);

    <V> V findById(Long id, Class<V> clazz);

}
