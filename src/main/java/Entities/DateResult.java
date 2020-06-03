package Entities;

import Parser.DateFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Сущность, которая наполяется данными о совершенных покупках
 */
public class DateResult extends DateFilter {

    final String type = "stat";
    Integer totalDays = execute(fromJson());
    List<InnerResult> customers = new ArrayList<>();
    Integer totalExpenses = totalExpensesResult(fromJson());
    Double avgExpenses = avgExpensesResult(fromJson());

    public static class InnerResult {

        String name;
        List<Map<String, Object>> purchases;
        Integer totalExpenses = 0;

        @Override
        public String toString() {
            return "InnerResult{" +
                    "name='" + name + '\'' +
                    ", purchases=" + purchases +
                    ", totalExpenses=" + totalExpenses +
                    '}';
        }
    }

    public void fillObject (String name, String productName, Integer productExpence){

        boolean contain = false;
        for (InnerResult customer:
             customers) {

            if (customer.name.equals(name)) {
                contain = true;

                //наполнение списка Purchases
                Map<String, Object> productDescription = new HashMap<>();
                productDescription.put("name", productName);
                productDescription.put("expenses", productExpence);

                //передача мапы productDescription в список purchases
                customer.purchases.add(productDescription);
                customer.totalExpenses += productExpence;
            }
        }

        if (!contain) {
            InnerResult customer = new InnerResult();
            customer.name = name;
            customer.purchases = new ArrayList<>();
            customer.totalExpenses = productExpence;

            Map<String, Object> productDescription = new HashMap<>();
            productDescription.put("name", productName);
            productDescription.put("expenses", productExpence);

            customer.purchases.add(productDescription);
            customers.add(customer);
        }
    };
}


