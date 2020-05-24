package Entities;

import Parser.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Result {
    final String type = "search";
    List<InnerResult> results = new ArrayList<>();

    public Result(List<InnerResult> results) {
        this.results = results;
    }

    public static class InnerResult {
        Map<String, Object> criteria;
        List<Buyers> results;

        @Override
        public String toString() {
            return "InnerResult{" +
                    "criteria=" + criteria +
                    ", results=" + results +
                    '}';
        }

        public InnerResult() {
            this.results = new ArrayList<>();
        }

        public void setCriteria(Map<String, Object> criteria) {
            this.criteria = criteria;
        }
        public void addResults(Buyers results) {
            this.results.add(results);
        }
    }

    public void addResults(InnerResult results) {
        this.results.add(results);
    }
}


