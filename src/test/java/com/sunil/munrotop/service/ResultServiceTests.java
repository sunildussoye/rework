package com.sunil.munrotop.service;

import com.sunil.munrotop.model.ResultDTO;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ResultServiceTests {

    @Mock
    private ResultService resultService;

    static List<ResultDTO> result = new ArrayList<>() ;

    @BeforeEach
    public void setup() {
        //if we don't call below, we will get NullPointerException
        MockitoAnnotations.initMocks(this);
    }

   @Test
    public void categoryInvalidShouldReturnEmptyResponse() throws Exception {
       System.out.println(resultService);
      /* when(resultService.filterWithParams("MUN",null,null,null,null)).thenReturn(result);
       System.out.println(result);
        assertEquals(result.size(),0);*/
    }





}
