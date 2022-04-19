package com.starreport.models;

import java.util.ArrayList;
import java.util.List;

public class People {
    private List<Person> results;

    public People() {
        results = new ArrayList<>();
    }

    public List<Person> getResults() {
        return results;
    }
}
