package Entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *  Сущность, которая данными из запросов (первое задание)
 */
public class Result {
    final String type = "search";
    List<InnerResult> results;

    public Result(List<InnerResult> results) {
        this.results = results;
    }

    public static class InnerResult {
        Map<String, Object> criteria;
        List<Buyers> results;

        public InnerResult() {
            this.results = new ArrayList<>();
        }

        public void setCriteria(Map<String, Object> criteria) {
            this.criteria = criteria;
        }

        public void addResults(Buyers results) {
            this.results.add(results);
        }

        @Override
        public String toString() {
            return "InnerResult{" +
                    "criteria=" + criteria +
                    ", results=" + results +
                    '}';
        }
    }
}


