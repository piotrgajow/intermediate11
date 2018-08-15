package pl.sda.intermediate11;

import com.google.common.collect.Maps;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomersTest {

    Customer[] people = new Customer[]{
            new Customer("Anna", "Nowak   ", 33, "1200"),
            new Customer("Beata", "Kowalska", 22, "1200"),
            new Customer("Marek", " Nowak", 25, "1250"),
            new Customer("Adam", "Twardowski", 33, "1100"),
            new Customer("Monika  ", "Kos", 25, "2500"),
            new Customer("Adam ", "Rudy", 45, "3333"),
            new Customer("Marek", "Rudy", 15, 2210),
            new Customer("Adam", "Madej", 15, 3333)
    };

    @Test
    void shouldCreateStatisticsWithSalary() {
        Map<BigDecimal, Integer> resultMap = Maps.newHashMap();
        for (Customer customer : people) {
            if (resultMap.containsKey(customer.getSalary())) {
                int innerCounter = resultMap.get(customer.getSalary());
                resultMap.put(customer.getSalary(), innerCounter + 1);
            } else {
                resultMap.put(customer.getSalary(), 1);
            }
        }
        Assertions.assertEquals(2, resultMap.get(BigDecimal.valueOf(1200)).intValue());
    }

    @Test
    void shouldCreateStatisticsWithSalaryStream() {
        Map<BigDecimal, Long> resultMap = Arrays.stream(people)
                .collect(Collectors.groupingBy(c -> c.getSalary(), Collectors.counting()));
        Assertions.assertEquals(2, resultMap.get(BigDecimal.valueOf(1200)).intValue());
        Assertions.assertEquals(2, resultMap.get(BigDecimal.valueOf(3333)).intValue());

    }
}
