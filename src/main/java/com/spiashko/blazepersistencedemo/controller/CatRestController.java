package com.spiashko.blazepersistencedemo.controller;

import com.spiashko.blazepersistencedemo.repository.CatRepository;
import com.spiashko.blazepersistencedemo.view.CatCreateView;
import com.spiashko.blazepersistencedemo.view.CatSimpleView;
import com.spiashko.blazepersistencedemo.view.CatWithOwnerView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CatRestController {

    private final CatRepository catRepository;

    @RequestMapping(path = "/cats", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@RequestBody CatCreateView catCreateView) {
        catRepository.save(catCreateView);
        return ResponseEntity.ok(catCreateView.getId().toString());
    }

    @RequestMapping(path = "/cats", method = RequestMethod.GET)
    public List<CatWithOwnerView> findAll() {
        List<CatWithOwnerView> resultPage = catRepository.findAll(CatWithOwnerView.class);
        return resultPage;
    }

    @RequestMapping(path = "/simple-cats", method = RequestMethod.GET)
    public List<CatSimpleView> findAllSimple() {
        List<CatSimpleView> resultPage = catRepository.findAllSimple();
        return resultPage;
    }

    @RequestMapping(path = "/with-owners-cats", method = RequestMethod.GET)
    public List<CatWithOwnerView> findAllWithOwner() {
        List<CatWithOwnerView> resultPage = catRepository.findAllWithOwner();
        return resultPage;
    }

}
