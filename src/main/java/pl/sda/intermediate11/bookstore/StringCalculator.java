package pl.sda.intermediate11.bookstore;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public class StringCalculator {

    public static int adding(String exampleData) {
        if (StringUtils.isBlank(exampleData)) {
            return 0;
        }
        if (StringUtils.contains(exampleData, ",")) {
            return Arrays.stream(exampleData.split("[,\n]"))
                    .filter(w->StringUtils.isNotBlank(w))
                    .map(w -> w.trim())
                    .map(w -> Integer.valueOf(w))
                    .reduce((a, b) -> a + b)
                    .orElse(0);
        } else {
            return Integer.valueOf(exampleData.trim());
        }
    }
}
