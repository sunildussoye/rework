package com.sunil.munrotop.service;

import com.sunil.munrotop.model.ClientResponse;
import com.sunil.munrotop.model.ResultDTO;
import com.sunil.munrotop.util.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ResultService {

    private List<ResultDTO> result = new ArrayList<>();

    private static final String FILE_NAME = "csv/munrotab.csv";
    private static final String HILL_CATEGORY_NAME = "MUN";
    private static final String HILL_CATEGORY_NAME_1 = "TOP";

    /*
   Filtering of search by hill category (i.e. Munro, Munro Top or either).
   TODO : QUERY MUN Not Munro / just TOP - according to the column values
   NOTE : My Assumption MUN /TOP
   If this information is not provided by the user it should default to either.
   This should use the “post 1997” column and
   if it is blank the hill should be always excluded from result
   */
    public List<ResultDTO> filterByCat(String category) {

        Predicate<ResultDTO> hillCategoryPredicate = getPredicate(category);

        return result.stream()
                .filter(hillCategoryPredicate)
                .filter(x -> !x.getHillCategory().isEmpty())
                .collect(Collectors.toList());
    }

     /*
    The ability to sort the results by height in meters and alphabetically by name.
    For both options it should be possibly to specify if this should be done in ascending
    or descending order
    TODO : QUERY NOTE : My Assumption for Ascending / Descending = (A or D)
    */

    public List<ResultDTO> filterByCatSortedByHeightAndName(String category, String heightOrder, String nameOrder) {

        Predicate<ResultDTO> hillCategoryPredicate = getPredicate(category);

        Comparator<ResultDTO> byHeight = heightOrder.equals("D") ?
                Comparator.comparing(ResultDTO::getHeight).reversed() :
                Comparator.comparing(ResultDTO::getHeight);

        Comparator<ResultDTO> byName = nameOrder.equals("D") ?
                Comparator.comparing(ResultDTO::getName).reversed() :
                Comparator.comparing(ResultDTO::getName);

        return result.stream()
                .filter(hillCategoryPredicate)
                .filter(x -> !x.getHillCategory().isEmpty()) // exclude any rows with blank category
                .sorted(byHeight.thenComparing(byName))
                .collect(Collectors.toList());
    }

    // Use the value from URL , if no value set , query will list either (all)
    // form part of the basic category query
    private Predicate<ResultDTO> getPredicate(String category) {

        Predicate<ResultDTO> hillCategoryPredicate = StringUtils.isEmpty(category) ?
                x -> (x.getHillCategory().equals(HILL_CATEGORY_NAME) || x.getHillCategory().equals(HILL_CATEGORY_NAME_1)) :
                x -> (x.getHillCategory().equals(category));
        return hillCategoryPredicate;
    }

    /*
    ● The ability to limit the total number of results returned, e.g. only show the top 10
     */

    // exclude category
    public List<ResultDTO> filterByCatWithLimit(int limit, ClientResponse errorResponse) {
        String category = null;

        if (limit <= 0) {
            errorResponse.withMessage(String.format("Limit must be specified and cannot be negative %d", limit));
            return null;
        }

        Predicate<ResultDTO> hillCategoryPredicate = getPredicate(category);

        return result.stream()
                .filter(hillCategoryPredicate)
                .filter(x -> !x.getHillCategory().isEmpty())
                .limit(limit)
                .collect(Collectors.toList());
    }

    /*
    The ability to specify a minimum height in meters
●    The ability to specify a maximum height in meters
    */

    // exclude category  
    public List<ResultDTO> filterByCatWithMaxMinHeight(int minHeight, int maxHeight, ClientResponse errorResponse) {
        String category = null;
        // validate limit - must be > than zero & not -ve
        if (minHeight <= 0) {
            errorResponse.withMessage(String.format("Min Height must be specified %d", minHeight));
            return null;
        }
        if (maxHeight <= 0) {
            errorResponse.withMessage(String.format("Max Height must be specified %d", maxHeight));
            return null;
        }

        if (maxHeight <= minHeight) {
            errorResponse.withMessage(String.format("Maximum Height(%d) must be greater than minimum height(%d)", maxHeight, minHeight));
            return null;
        }

        Predicate<ResultDTO> hillCategoryPredicate = getPredicate(category);

        return result.stream()
                .filter(hillCategoryPredicate)
                .filter(x -> !x.getHillCategory().isEmpty())
                .filter(x -> x.getHeight().compareTo(BigDecimal.valueOf(minHeight)) >= 0 &&
                        x.getHeight().compareTo(BigDecimal.valueOf(maxHeight)) <= 0)
                .collect(Collectors.toList());
    }

    @PostConstruct
    public void loadData() {
        result = ResultUtil.CSVToObject((FILE_NAME));
    }
}
