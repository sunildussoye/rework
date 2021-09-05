package com.sunil.munrotop.controller;

import com.sunil.munrotop.model.ResultDTO;
import com.sunil.munrotop.service.ResultService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(ResultController.class)
@AutoConfigureMockMvc
public class ResultControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ResultService resultService;

    @Test
    public void list_basic() throws Exception {

        List<ResultDTO> res = Collections.singletonList(new ResultDTO("Mun", new BigDecimal("1999.9"), "any", "12345"));

        when(Collections.unmodifiableList(resultService.filterWithParams("MUN", null, null, null, null))).thenReturn(res);
        RequestBuilder request = MockMvcRequestBuilders
                .get("/list")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("[{hillCategory:'MUN',height:1999.9,name:'any',gridReference: '12345'}]"))
                .andReturn();
        System.out.println(result);
        //JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

    }

}

