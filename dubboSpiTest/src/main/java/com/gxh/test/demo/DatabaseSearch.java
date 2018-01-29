package com.gxh.test.demo;

public class DatabaseSearch implements Search {

    private String name = "database_search";
    private Read read;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Read getRead() {
        return read;
    }

    public void setRead(Read read) {
        this.read = read;
    }

    public void doSearch() {
        System.out.println("this is database search");
    }
}
