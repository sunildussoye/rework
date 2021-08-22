package com.sunil.munrotop.controller;


import com.sunil.munrotop.model.ClientResponse;
import com.sunil.munrotop.model.ResultDTO;
import com.sunil.munrotop.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ResultController {

    @Autowired
    private ResultService service;

    @GetMapping(value = {"/category", "/category/{category}"})
    public List<ResultDTO> filterByCat(@PathVariable(required = false) String category) {

        return service.filterByCat(category);
    }

    @GetMapping(value = {"/category/sorted", "/category/sorted/{category}/{heightOrder}/{nameOrder}"})
    public List<ResultDTO> filterByCatSortedByHeightAndName(@PathVariable (required = false) String category,
                                                        @PathVariable (required = false) String heightOrder,
                                                        @PathVariable (required = false) String nameOrder) {

        return service.filterByCatSortedByHeightAndName(category,heightOrder,nameOrder);
    }

    // TODO - READ RESPONSE ENTITY
    @GetMapping(value = { "/category/limit/{limit}"})
    public ResponseEntity<Object> filterByCatWithLimit(@PathVariable int limit) {

        ClientResponse errorResponse = new ClientResponse();

        List<ResultDTO> result= service.filterByCatWithLimit(limit,errorResponse);

        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @GetMapping(value = { "/category/height/{minHeight}/{maxHeight}"})
    public ResponseEntity<Object> filterByCatWithMaxMinHeight(@PathVariable int minHeight,
                                                              @PathVariable int maxHeight) {

        ClientResponse errorResponse = new ClientResponse();

        List<ResultDTO> result= service.filterByCatWithMaxMinHeight(minHeight,maxHeight,errorResponse);

        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }



}
