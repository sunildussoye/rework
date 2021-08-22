package com.sunil.munrotop.util;

import com.sunil.munrotop.exception.ResultException;
import com.sunil.munrotop.model.ResultDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


public class ResultUtilTests {

    List<ResultDTO> resultDTOList = new ArrayList<>();

    @BeforeEach
    public void setup() {

        resultDTOList = ResultUtil.CSVToObject("csv/munrotab.csv");
    }

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

        assertThat(!resultDTOList.isEmpty()) ;
    }

    @Test
    public void CSVReadExactNumberOfLines() {
        assertEquals(602, resultDTOList.size());
    }

    @Test
    public void CSVContainsNoBlankLine() {
        List<ResultDTO> emptyLine = resultDTOList.stream()
                .filter(e -> e.getName().isEmpty())
                .collect(Collectors.toList());

        assertEquals(0, emptyLine.size());
    }

    @Test
    public void CSVContainsAValidHillCategory() {
        List<ResultDTO> category = resultDTOList.stream()
                .filter(e -> e.getHillCategory().equals("MUN"))
                .collect(Collectors.toList());

        assertEquals(282, category.size());
    }

}
