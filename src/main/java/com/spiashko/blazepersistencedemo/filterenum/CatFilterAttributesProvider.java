package com.spiashko.blazepersistencedemo.filterenum;

import com.blazebit.text.FormatUtils;
import com.blazebit.text.SerializableFormat;
import com.spiashko.blazepersistencedemo.model.Cat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class CatFilterAttributesProvider implements FilterAttributesProvider<Cat> {

    public static final String[] ATTRIBUTES = {"id", "name", "dob", "owner.id", "owner.name"};

    public static final Map<String, SerializableFormat<?>> FILTER_ATTRIBUTES;


    static {
        FILTER_ATTRIBUTES = Map.of(
                "id", FormatUtils.getAvailableFormatters().get(Long.class),
                "name", FormatUtils.getAvailableFormatters().get(String.class),
                "dob", FormatUtils.getAvailableFormatters().get(LocalDate.class),
                "owner.id", FormatUtils.getAvailableFormatters().get(Long.class),
                "owner.name", FormatUtils.getAvailableFormatters().get(String.class)
        );
    }


    @Override
    public Map<String, SerializableFormat<?>> getFilterAttributes() {
        return FILTER_ATTRIBUTES;
    }
}
