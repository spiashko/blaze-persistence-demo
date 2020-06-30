package com.spiashko.blazepersistencedemo.filterenum;

import com.blazebit.text.FormatUtils;
import com.blazebit.text.SerializableFormat;
import com.spiashko.blazepersistencedemo.model.Person;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@RequiredArgsConstructor
public class PersonFilterAttributesProvider implements FilterAttributesProvider<Person> {

    public static final Map<String, SerializableFormat<?>> FILTER_ATTRIBUTES;

    static {
        FILTER_ATTRIBUTES = Map.of(
                "id", FormatUtils.getAvailableFormatters().get(Long.class),
                "name", FormatUtils.getAvailableFormatters().get(String.class)
        );
    }

    @Override
    public Map<String, SerializableFormat<?>> getFilterAttributes() {
        return FILTER_ATTRIBUTES;
    }
}
