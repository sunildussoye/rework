package com.sunil.munrotop.controller;

import com.sunil.munrotop.model.ResultDTO;
import com.sunil.munrotop.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ResultController {

    @Autowired
    private ResultService service;

    // IAIN - Do you need to return a ResponseEntity here?  Why not just return the List?
    // IAIN - What happens if someone sends a request where the limit is set to "AAA"?

   // Use wrapper instead of primitive - for null checks
    @GetMapping(value = {"/list"})
    public ResponseEntity<List<ResultDTO>> filterByCatSortedByHeightAndName(@RequestParam (value="category", defaultValue="All") String category,
                                                            @RequestParam (value="maxHeight",required = false) Integer maxHeight,
                                                            @RequestParam (value="minHeight",required = false) Integer minHeight,
                                                            @RequestParam (value="limit"    ,required = false) Integer limit,
                                                            @RequestParam (value="sort"     ,required = false) String[] sort ) {

        List<ResultDTO> result= service.filterWithParams(category,maxHeight,minHeight,limit, sort);

        // IAIN - Can the List be null at this point?  Do you need the if statement at all?
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().build();
        }


    }

}
