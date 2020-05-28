package Entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DateResult {

    final String type = "stat";

    Integer totalDays;
    List<InnerResult> customers = new ArrayList<>();

    public Integer getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(Integer totalDays) {
        this.totalDays = totalDays;
    }

    public List<InnerResult> getCustomers() {
        return customers;
    }

    public void setCustomers(List<InnerResult> customers) {
        this.customers = customers;
    }

    public static class InnerResult {

        String name;
        List<Map<String, Object>> purchases;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Map<String, Object>> getPurchases() {
            return purchases;
        }

        public void setPurchases(List<Map<String, Object>> purchases) {
            this.purchases = purchases;
        }
    }

    public void addResults(InnerResult results) {
        this.customers.add(results);
    }

    //реализовать метод, который проверят нахождение оппределённого человека в списке, если человек есть то мы добавляем его в список с его покупками.
}


