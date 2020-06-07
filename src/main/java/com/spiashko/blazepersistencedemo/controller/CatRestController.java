package com.spiashko.blazepersistencedemo.controller;

import com.blazebit.persistence.spring.data.webmvc.EntityViewId;
import com.spiashko.blazepersistencedemo.repository.CatRepository;
import com.spiashko.blazepersistencedemo.view.cat.managment.CatCreateView;
import com.spiashko.blazepersistencedemo.view.cat.managment.CatUpdateView;
import com.spiashko.blazepersistencedemo.view.cat.retrieve.CatSimpleView;
import com.spiashko.blazepersistencedemo.view.cat.retrieve.CatWithOwnerView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CatRestController {

    private final CatRepository catRepository;

    @RequestMapping(path = "/cats", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CatWithOwnerView create(@RequestBody CatCreateView catCreateView) {
        CatCreateView cat = catRepository.save(catCreateView);
        return catRepository.findById(cat.getId(), CatWithOwnerView.class);
    }

    @RequestMapping(path = "/cats/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CatWithOwnerView updateCat(@PathVariable Long id,
                                      @EntityViewId("id") @RequestBody CatUpdateView catUpdateView) {
        CatUpdateView cat = catRepository.save(catUpdateView);
        return catRepository.findById(cat.getId(), CatWithOwnerView.class);
    }

    @RequestMapping(path = "/cats", method = RequestMethod.GET)
    public List<CatSimpleView> findAll() {
        List<CatSimpleView> result = catRepository.findAll(CatSimpleView.class);
        return result;
    }

    @RequestMapping(path = "/cats/{id}", method = RequestMethod.GET)
    public CatWithOwnerView findById(@PathVariable Long id) {
        CatWithOwnerView result = catRepository.findById(id, CatWithOwnerView.class);
        return result;
    }

}
