package Entities;

import Parser.DateFilter;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DateResult extends DateFilter {

    final String type = "stat";
    Integer totalDays;

    {
        try {
            totalDays = execute(fromJson());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    List<InnerResult> customers = new ArrayList<>();

    public DateResult() {

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

    }

    Integer totalExpenses = totalExpensesResult(fromJson());
    Double avgExpenses = avgExpensesResult(fromJson());

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


