package com.spiashko.blazepersistencedemo.filterenum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CatAttributesEnum {

    ID("id"),
    NAME("name"),
    DOB("dob"),
    OWNER__ID("owner.id"),
    OWNER__NAME("owner.name");

    private final String value;
}
