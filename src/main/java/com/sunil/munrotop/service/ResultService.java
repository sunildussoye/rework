package com.sunil.munrotop.service;

import com.sunil.munrotop.model.ResultDTO;
import com.sunil.munrotop.util.ResultUtil;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ResultService {

    private List<ResultDTO> result = new ArrayList<>();

    private   String FILE_NAME = "csv/munrotab.csv";

   // TODO :Need to try using  predicate classes .. see Iain's mail ..

    public List<ResultDTO> filterWithParams(String category, Integer maxHeight, Integer minHeight, Integer limit, String[] sort) {
        // deal with all predicates
        List<Predicate<ResultDTO>> allPredicates = ResultUtil.filterPredicate(category,maxHeight,minHeight,limit);
        // deal with sort comparators

        List<Comparator<ResultDTO>> sortComparator = ResultUtil.sortComparator(sort);

       // combine all AND predicates (x-> true)
        return result.stream().filter(allPredicates.stream().reduce(x -> true, Predicate::and))
                .limit(limit != null ? limit : result.size())
                .sorted(sortComparator.get(0).thenComparing(sortComparator.get(1)))
                .collect(Collectors.toList());
    }

    @PostConstruct
    public void loadData() {
        result = ResultUtil.CSVToObject((FILE_NAME));
    }

}
