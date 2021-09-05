package com.sunil.munrotop.util;

import com.sunil.munrotop.exception.ResultException;
import com.sunil.munrotop.model.ResultDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


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

    @Test
    public void returnCorrectNumberOfPredicateThree() {

        List<Predicate<ResultDTO>> res = ResultUtil.filterPredicate("CAL", 300, 200, 10);
        assertEquals(3,res.size());
    }

    @Test
    public void returnCorrectNumberOfPredicateThreeAgain() {

        List<Predicate<ResultDTO>> res = ResultUtil.filterPredicate("CAL", 300, 200,null);
        assertEquals(3,res.size());
    }
    @Test
    public void returnCorrectNumberOfPredicateTwo() {

        List<Predicate<ResultDTO>> res = ResultUtil.filterPredicate("CAL", null, null,1);
        assertEquals(2,res.size());
    }

    @Test
    public void returnCorrectNumberOfPredicateTwoAgain() {

        List<Predicate<ResultDTO>> res = ResultUtil.filterPredicate(null, null, null,1);
        assertEquals(2,res.size());
    }

    @Test
    public void returnCorrectNumberOfComparatorEvenIfNull() {

        List<Comparator<ResultDTO>> res = ResultUtil.sortComparator(null);
        assertEquals(2,res.size());
    }

    @Test
    public void returnValidationErrorWhenMinIsZero() {

        Exception exception = assertThrows(ResultException.class, () -> {
            ResultUtil.ValidateFilterCriteria(200,0,null);;
        });

        String expectedMessage = "Min Height must be specified 0";
        String actualMessage   = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    public void returnValidationErrorWhenMaxIsZero() {

        Exception exception = assertThrows(ResultException.class, () -> {
            ResultUtil.ValidateFilterCriteria(0,10,null);;
        });

        String expectedMessage = "Max Height must be specified 0";
        String actualMessage   = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    public void returnValidationErrorWhenMinIsGraterThanMax() {

        Exception exception = assertThrows(ResultException.class, () -> {
            ResultUtil.ValidateFilterCriteria(200,10000,null);;
        });

        String expectedMessage = "Maximum Height(200) must be greater than minimum height(10000)";
        String actualMessage   = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }


}
