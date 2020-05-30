package Entities;

import java.util.ArrayList;
import java.util.HashMap;
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

        @Override
        public String toString() {
            return "InnerResult{" +
                    "name='" + name + '\'' +
                    ", purchases=" + purchases +
                    '}';
        }

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

    @Override
    public String toString() {
        return "DateResult{" +
                "type='" + type + '\'' +
                ", totalDays=" + totalDays +
                ", customers=" + customers +
                '}';
    }

    public void fillObject (String name, String productName, Integer productExpence){
        boolean contain = false;
        for (InnerResult customer:
             customers) {
            if (customer.name.equals(name)) {
                contain = true;
                Map<String, Object> productDescription = new HashMap<>();
                productDescription.put("name", productName);
                productDescription.put("expenses", productExpence);
                customer.purchases.add(productDescription);
            }
        }
        if (!contain) {
            InnerResult customer = new InnerResult();
            customer.name = name;
            customer.purchases = new ArrayList<>();
            Map<String, Object> productDescription = new HashMap<>();
            productDescription.put("name", productName);
            productDescription.put("expenses", productExpence);
            customer.purchases.add(productDescription);
            customers.add(customer);
        }
    };

}


