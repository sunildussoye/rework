package com.sunil.munrotop.util;

import com.sunil.munrotop.exception.ResultException;
import com.sunil.munrotop.model.ResultDTO;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


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

    @Test
    public void CSVReadExactNumberOfLines() {
        List<ResultDTO> result = ResultUtil.CSVToObject("csv/munrotab.csv");
        assertEquals(602, result.size());
    }

    @Test
    public void CSVContainsNoBlankLine() {
        List<ResultDTO> result = ResultUtil.CSVToObject("csv/munrotab.csv");
        List<ResultDTO> emptyLine = result.stream()
                .filter(e -> e.getName().isEmpty())
                .collect(Collectors.toList());

        assertEquals(0, emptyLine.size());

    }

    @Test
    public void CSVContainsAValidHillCategory() {
        List<ResultDTO> result = ResultUtil.CSVToObject("csv/munrotab.csv");
        List<ResultDTO> category = result.stream()
                .filter(e -> e.getHillCategory().equals("MUN"))
                .collect(Collectors.toList());

        assertEquals(282, category.size());

    }

    
}
