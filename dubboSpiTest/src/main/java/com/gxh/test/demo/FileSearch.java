package com.gxh.test.demo;

public class FileSearch implements Search {

    private String name = "file_search";
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
        System.out.println("this is file search");
    }
}
