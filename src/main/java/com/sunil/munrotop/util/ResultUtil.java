package com.sunil.munrotop.util;

import com.opencsv.bean.CsvToBeanBuilder;
import com.sunil.munrotop.exception.ResultException;
import com.sunil.munrotop.model.ResultDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ResultUtil {

    public static List<ResultDTO> CSVToObject(String fileName) {

        try { Resource resource = new ClassPathResource(fileName);
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
}
