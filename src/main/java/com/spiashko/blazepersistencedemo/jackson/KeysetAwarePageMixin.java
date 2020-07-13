package com.spiashko.blazepersistencedemo.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public interface KeysetAwarePageMixin {

    @JsonProperty("contentttttt")
    List<?> getContent();

}
