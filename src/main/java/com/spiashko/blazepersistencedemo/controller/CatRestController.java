package com.spiashko.blazepersistencedemo.controller;

import com.blazebit.persistence.spring.data.repository.KeysetPageable;
import com.blazebit.persistence.spring.data.webmvc.EntityViewId;
import com.blazebit.persistence.spring.data.webmvc.KeysetConfig;
import com.spiashko.blazepersistencedemo.config.BlazeFilterQueryParam;
import com.spiashko.blazepersistencedemo.config.BlazePageableAsQueryParam;
import com.spiashko.blazepersistencedemo.filter.Filter;
import com.spiashko.blazepersistencedemo.filter.SpecificationBuilder;
import com.spiashko.blazepersistencedemo.filterenum.CatFilterAttributesProvider;
import com.spiashko.blazepersistencedemo.filterenum.PersonFilterAttributesProvider;
import com.spiashko.blazepersistencedemo.model.Cat;
import com.spiashko.blazepersistencedemo.repository.CatRepository;
import com.spiashko.blazepersistencedemo.rsql.AndPathVarEq;
import com.spiashko.blazepersistencedemo.rsql.RsqlSpec;
import com.spiashko.blazepersistencedemo.view.cat.managment.CatCreateView;
import com.spiashko.blazepersistencedemo.view.cat.managment.CatUpdateView;
import com.spiashko.blazepersistencedemo.view.cat.retrieve.CatSimpleView;
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

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CatRestController {

    private final PersonFilterAttributesProvider personFilterAttributesProvider = new PersonFilterAttributesProvider();
    private final CatFilterAttributesProvider catFilterAttributesProvider = new CatFilterAttributesProvider();
    private final CatRepository repository;
    private final SpecificationBuilder specificationBuilder;

    @RequestMapping(path = "/cats", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CatWithOwnerView create(@RequestBody CatCreateView catCreateView) {
        CatCreateView cat = repository.saveView(catCreateView);
        return repository.findById(cat.getId(), CatWithOwnerView.class);
    }

    @Parameter(in = ParameterIn.PATH
            , name = "id"
            , content = @Content(schema = @Schema(type = "integer")))
    @RequestMapping(path = "/cats/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CatWithOwnerView updateCat(@EntityViewId("id") @RequestBody CatUpdateView catUpdateView) {
        CatUpdateView cat = repository.saveView(catUpdateView);
        return repository.findById(cat.getId(), CatWithOwnerView.class);
    }

    @RequestMapping(path = "/cats", method = RequestMethod.GET)
    public List<CatSimpleView> findAll(
            @RsqlSpec Specification<Cat> spec
    ) {
        List<CatSimpleView> result = repository.findAll(CatSimpleView.class, spec);
        return result;
    }

    @Parameter(in = ParameterIn.PATH
            , name = "ownerId"
            , content = @Content(schema = @Schema(type = "integer")))
    @BlazeFilterQueryParam
    @RequestMapping(path = "/owner/{ownerId}/cats", method = RequestMethod.GET)
    public List<CatSimpleView> findAllByOwner(
            @Parameter(hidden = true)
            @RsqlSpec(
                    extensionFromPath = @AndPathVarEq(pathVar = "ownerId", attributePath = "owner.id")
            ) Specification<Cat> spec
    ) {
        List<CatSimpleView> result = repository.findAll(CatSimpleView.class, spec);
        return result;
    }

    @BlazeFilterQueryParam
    @BlazePageableAsQueryParam
    @RequestMapping(path = "/pageable-cats", method = RequestMethod.GET)
    public Page<CatSimpleView> findAllPageable(
            @Parameter(hidden = true) @KeysetConfig(Cat.class) KeysetPageable keysetPageable,
            @Parameter(hidden = true) @RequestParam(name = "filter", required = false) final Filter[] filter
    ) {
        Specification<Cat> specification = specificationBuilder.build(filter, catFilterAttributesProvider);
        Page<CatSimpleView> result = repository.findAll(specification, keysetPageable);
        return result;
    }

    @RequestMapping(path = "/cats/{id}", method = RequestMethod.GET)
    public CatWithOwnerView findById(@PathVariable Long id) {
        CatWithOwnerView result = repository.findById(id, CatWithOwnerView.class);
        return result;
    }

}
