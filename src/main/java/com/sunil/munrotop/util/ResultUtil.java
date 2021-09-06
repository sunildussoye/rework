package com.sunil.munrotop.util;

import com.opencsv.bean.CsvToBeanBuilder;
import com.sunil.munrotop.exception.ResultException;
import com.sunil.munrotop.model.ResultDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;


// IAIN - Why have this as a separate class?  It's only used by ResultService so why not just put the methods there?
public class ResultUtil {

    public static List<ResultDTO> CSVToObject(String fileName) {

        try {
            // IAIN - FileReader should be used in a try with resources so that it is closed properly
            Resource resource = new ClassPathResource(fileName);
            List<ResultDTO> results = new CsvToBeanBuilder(new FileReader(resource.getFile()))
                    .withType(ResultDTO.class)
                    .build().parse();

            // remove - blank lines
            results.removeIf(e -> StringUtils.isEmpty(e.getName()));

            // show in log - can use a logger
            results.forEach(System.out::println);
            return results;
        } catch (IOException e) {
            throw new ResultException("File " + fileName + " not found");
        }
    }

    public static void ValidateFilterCriteria(Integer maxHeight, Integer minHeight, Integer limit) {

        // IAIN - What happens if only minHeight is specified?  The requirements allow the calling code to only provide one.
        if (minHeight != null && maxHeight != null) {

            if (minHeight <= 0) {
                throw new ResultException((String.format("Min Height must be specified %d", minHeight)));
            }

            if (maxHeight <= 0) {
                throw new ResultException(String.format("Max Height must be specified %d", maxHeight));
            }

            if (maxHeight <= minHeight) {
                throw new ResultException(String.format("Maximum Height(%d) must be greater than minimum height(%d)", maxHeight, minHeight));
            }
        }

        if (limit != null && limit <= 0) {
            throw new ResultException((String.format("Limit must be specified and cannot be negative %d", limit)));
        }
    }

    // IAIN - How do I sort by name and then height or just by height?
    public static List<Comparator<ResultDTO>> sortComparator(String[] sort) {
        String heightOrder = "";
        String nameOrder = "";

        // IAIN - What happens if send an array with only one entry?
        if (sort != null && sort.length > 0) {
            heightOrder = sort[0].split(":")[1];
            nameOrder = sort[1].split(":")[1];
        }

        Comparator<ResultDTO> byHeight = StringUtils.isNotEmpty(heightOrder) && heightOrder.equalsIgnoreCase("Desc") ?
                Comparator.comparing(ResultDTO::getHeight).reversed() :
                Comparator.comparing(ResultDTO::getHeight);

        Comparator<ResultDTO> byName = StringUtils.isNotEmpty(nameOrder) && nameOrder.equalsIgnoreCase("Desc") ?
                Comparator.comparing(ResultDTO::getName).reversed() :
                Comparator.comparing(ResultDTO::getName);

        return Arrays.asList(byHeight, byName);
    }

    public static List<Predicate<ResultDTO>> filterPredicate(String category, Integer maxHeight, Integer minHeight, Integer limit) {

        ValidateFilterCriteria(maxHeight, minHeight, limit);

        List<Predicate<ResultDTO>> allPredicates = new ArrayList<>();

        //Base Filters
        Predicate<ResultDTO> hillCategoryPredicate = ResultUtil.getPredicate(category);
        // IAIN - Why not remove these entries during the file load?
        // IAIN - This is also the second predicate to remove blank categories and unnecessary (see line 114)
        Predicate<ResultDTO> removeHillWithBlankNames = x -> !x.getHillCategory().isEmpty();

        allPredicates.add(hillCategoryPredicate);
        allPredicates.add(removeHillWithBlankNames);

        // IAIN - What happens if only minHeight is specified?  The requirements allow the calling code to only provide one.
        // add  heightpredicate
        if (minHeight != null && maxHeight != null) {
            allPredicates.add(x -> x.getHeight().compareTo(BigDecimal.valueOf(minHeight)) >= 0 && x.getHeight().compareTo(BigDecimal.valueOf(maxHeight)) <= 0);
        }

        return allPredicates;
    }

    public static Predicate<ResultDTO> getPredicate(String category) {
        return StringUtils.isEmpty(category) || category.equals("All") ?
                x -> (!x.getHillCategory().isEmpty()) :
                x -> (x.getHillCategory().equalsIgnoreCase(category));
    }

}
