package com.spiashko.blazepersistencedemo.controller;

import com.spiashko.blazepersistencedemo.repository.PersonRepository;
import com.spiashko.blazepersistencedemo.view.PersonCreateView;
import com.spiashko.blazepersistencedemo.view.PersonSimpleView;
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
public class PersonRestController {

    private final PersonRepository repository;

    @RequestMapping(path = "/persons", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@RequestBody PersonCreateView createView) {
        PersonCreateView result = repository.save(createView);
        return ResponseEntity.ok(result.getId().toString());
    }

    @RequestMapping(path = "/persons", method = RequestMethod.GET)
    public List<PersonSimpleView> findAll() {
        List<PersonSimpleView> result = repository.findAll();
        return result;
    }


}
