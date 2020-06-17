package com.spiashko.blazepersistencedemo.repository;

import com.blazebit.persistence.spring.data.repository.KeysetAwarePage;
import com.spiashko.blazepersistencedemo.model.Cat;
import com.spiashko.blazepersistencedemo.view.cat.retrieve.CatSimpleView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface CatRepository extends BaseRepo<Cat> {

    /**
     * Class<V> clazz can not be used as it true in
     * .m2/repository/org/springframework/data/spring-data-commons/2.2.6.RELEASE/spring-data-commons-2.2.6.RELEASE-sources.jar!/org/springframework/data/repository/core/support/RepositoryFactorySupport.java:617
     * but to make pageable work invocation must go through
     * /home/siarhei/.m2/repository/org/springframework/data/spring-data-commons/2.2.6.RELEASE/spring-data-commons-2.2.6.RELEASE-sources.jar!/org/springframework/data/repository/core/support/RepositoryFactorySupport.java:657
     * so ImplementationMethodExecutionInterceptor must be executed instead of QueryExecutorMethodInterceptor
     */
    KeysetAwarePage<CatSimpleView> findAll(Specification<Cat> specification, Pageable pageable);

}
