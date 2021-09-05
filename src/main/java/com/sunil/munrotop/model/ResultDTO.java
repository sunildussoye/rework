package com.sunil.munrotop.model;

import com.opencsv.bean.CsvBindByName;

import java.math.BigDecimal;

public class ResultDTO {
    @CsvBindByName(column = "Post 1997")
    private String hillCategory;
    @CsvBindByName(column = "Height (m)")
    private BigDecimal height;
    @CsvBindByName(column = "Name")
    private String name;
    @CsvBindByName(column = "Grid Ref")
    private String gridReference;

    public ResultDTO() {

    }
    public ResultDTO(String hillCategory, BigDecimal height, String name, String gridReference) {
        this.hillCategory = hillCategory;
        this.height = height;
        this.name = name;
        this.gridReference = gridReference;
    }

    public String getHillCategory() {

        return hillCategory;
    }

    public void setHillCategory(String hillCategory) {

        this.hillCategory = hillCategory;
    }

    public BigDecimal getHeight() {

        return height;
    }

    public void setHeight(BigDecimal height) {

        this.height = height;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getGridReference() {

        return gridReference;
    }

    public void setGridReference(String gridReference) {

        this.gridReference = gridReference;
    }

    @Override
    public String toString() {
        return "Munro {" +
                "name='" + name + '\'' +
                "height='" + height + '\'' +
                "hillCategory='" + hillCategory + '\'' +
                ", getGridReference=" + gridReference +
                '}';
    }
}
