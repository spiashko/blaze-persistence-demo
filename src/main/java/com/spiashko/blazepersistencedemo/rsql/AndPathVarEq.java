package com.spiashko.blazepersistencedemo.rsql;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface AndPathVarEq {

    String pathVar();
    String attributePath();

}
