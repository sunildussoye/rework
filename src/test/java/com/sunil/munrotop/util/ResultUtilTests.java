package com.sunil.munrotop.util;

import com.sunil.munrotop.exception.ResultException;
import com.sunil.munrotop.model.ResultDTO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ResultUtilTests {

    @Test
    public void CSVFileDoesNotExistWithException() {

        Exception exception = assertThrows(ResultException.class, () -> {
            ResultUtil.CSVToObject("csv/munrotabxx.csv");
        });

        String expectedMessage = "File csv/munrotabxx.csv not found";
        String actualMessage   = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void CSVFileCanBeParsed() {

        List<ResultDTO> result = ResultUtil.CSVToObject("csv/munrotab.csv");

        assertThat(!result.isEmpty()) ;
    }



}
