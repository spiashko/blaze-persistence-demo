package com.spiashko.blazepersistencedemo.rsql;

import com.spiashko.blazepersistencedemo.filterenum.FilterAttributesProvider;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface RsqlSpec {

    Class<? extends FilterAttributesProvider<?>> value();

}
