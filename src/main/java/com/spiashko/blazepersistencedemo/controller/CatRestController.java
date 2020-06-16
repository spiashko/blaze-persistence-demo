package com.spiashko.blazepersistencedemo.controller;

import com.blazebit.persistence.spring.data.repository.KeysetPageable;
import com.blazebit.persistence.spring.data.webmvc.EntityViewId;
import com.blazebit.persistence.spring.data.webmvc.KeysetConfig;
import com.blazebit.text.FormatUtils;
import com.blazebit.text.SerializableFormat;
import com.spiashko.blazepersistencedemo.config.BlazeFilterQueryParam;
import com.spiashko.blazepersistencedemo.config.BlazePageableAsQueryParam;
import com.spiashko.blazepersistencedemo.filter.Filter;
import com.spiashko.blazepersistencedemo.model.Cat;
import com.spiashko.blazepersistencedemo.repository.CatPageRepository;
import com.spiashko.blazepersistencedemo.repository.CatRepository;
import com.spiashko.blazepersistencedemo.service.SpecificationBuilder;
import com.spiashko.blazepersistencedemo.view.cat.managment.CatCreateView;
import com.spiashko.blazepersistencedemo.view.cat.managment.CatUpdateView;
import com.spiashko.blazepersistencedemo.view.cat.retrieve.CatSimpleView;
import com.spiashko.blazepersistencedemo.view.cat.retrieve.CatSimpleView_;
import com.spiashko.blazepersistencedemo.view.cat.retrieve.CatWithOwnerView;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class CatRestController {

    private static final Map<String, SerializableFormat<?>> FILTER_ATTRIBUTES;

    static {
        Map<String, SerializableFormat<?>> filterAttributes = new HashMap<>();
        filterAttributes.put(CatSimpleView_.ID, FormatUtils.getAvailableFormatters().get(Long.class));
        filterAttributes.put(CatSimpleView_.NAME, FormatUtils.getAvailableFormatters().get(String.class));
        filterAttributes.put(CatSimpleView_.DOB, FormatUtils.getAvailableFormatters().get(LocalDate.class));
        filterAttributes.put(CatSimpleView_.OWNER_ID, FormatUtils.getAvailableFormatters().get(Long.class));
        FILTER_ATTRIBUTES = Collections.unmodifiableMap(filterAttributes);
    }

    private final CatRepository repository;
    private final CatPageRepository pageRepository;
    private final SpecificationBuilder specificationBuilder;

    @RequestMapping(path = "/cats", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CatWithOwnerView create(@RequestBody CatCreateView catCreateView) {
        CatCreateView cat = repository.save(catCreateView);
        return repository.findById(cat.getId(), CatWithOwnerView.class);
    }

    @Parameter(in = ParameterIn.PATH
            , name = "id"
            , content = @Content(schema = @Schema(type = "integer")))
    @RequestMapping(path = "/cats/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CatWithOwnerView updateCat(@EntityViewId("id") @RequestBody CatUpdateView catUpdateView) {
        CatUpdateView cat = repository.save(catUpdateView);
        return repository.findById(cat.getId(), CatWithOwnerView.class);
    }

    @BlazeFilterQueryParam
    @RequestMapping(path = "/cats", method = RequestMethod.GET)
    public List<CatSimpleView> findAll(
            @Parameter(hidden = true) @RequestParam(name = "filter", required = false) final Filter[] filter
    ) {
        Specification<CatSimpleView> specification = specificationBuilder.build(filter, FILTER_ATTRIBUTES);
        List<CatSimpleView> result = repository.findAll(CatSimpleView.class, specification);
        return result;
    }

    @BlazeFilterQueryParam
    @BlazePageableAsQueryParam
    @RequestMapping(path = "/pageable-cats", method = RequestMethod.GET)
    public Page<CatSimpleView> findAllPageable(
            @Parameter(hidden = true) @KeysetConfig(Cat.class) KeysetPageable keysetPageable,
            @Parameter(hidden = true) @RequestParam(name = "filter", required = false) final Filter[] filter
    ) {
        Specification<CatSimpleView> specification = specificationBuilder.build(filter, FILTER_ATTRIBUTES);
        Page<CatSimpleView> result = pageRepository.findAll(specification, keysetPageable);
        return result;
    }

    @RequestMapping(path = "/cats/{id}", method = RequestMethod.GET)
    public CatWithOwnerView findById(@PathVariable Long id) {
        CatWithOwnerView result = repository.findById(id, CatWithOwnerView.class);
        return result;
    }

}
