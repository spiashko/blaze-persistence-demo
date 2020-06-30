package com.spiashko.blazepersistencedemo.filterenum;

import com.blazebit.text.SerializableFormat;

import java.util.Map;

public interface FilterAttributesProvider<T> {

    Map<String, SerializableFormat<?>> getFilterAttributes();

}
