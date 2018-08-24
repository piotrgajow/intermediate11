package pl.sda.intermediate11;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LambdasTest {

    @FunctionalInterface
    public interface SuperChecker {
        boolean check(Integer value);
    }

    private class OddChecker implements SuperChecker {
        @Override
        public boolean check(Integer value) {
            return value % 2 != 0;
        }
    }

    @Test
    void checkerTest() {
        OddChecker oddChecker = new OddChecker();
        Assert.assertTrue(oddChecker.check(3));

        SuperChecker superChecker = new SuperChecker() {
            @Override
            public boolean check(Integer value) {
                return value % 2 != 0;
            }
        };

        Assert.assertTrue(superChecker.check(3));

        SuperChecker labdaSuperChecker = e -> e % 2 != 0;
    }

    @FunctionalInterface
    public interface MathOperation {
        int operation(int a, int b);

        default String message() {
            return "BUM!";
        }
    }

    @Test
    void mathOperationTest() {
        MathOperation adding = (a, b) -> a + b;
        MathOperation subtracting = (a, b) -> a - b;
        MathOperation multiplication = (a, b) -> a * b;
        MathOperation division = (a, b) -> a / b;

        Assertions.assertEquals(7, adding.operation(2, 5));
        Assertions.assertEquals(-3, subtracting.operation(2, 5));
        Assertions.assertEquals(10, multiplication.operation(2, 5));
        Assertions.assertEquals(0, division.operation(2, 5));

    }

    @FunctionalInterface
    public interface MyBiComparator<T, U> {
        int biCompare(T obj1, U obj2);
    }

    @Test
    void compareTwoTypes() {
        Integer number = 20;
        String text = "123";

        MyBiComparator<Integer, String> myBiComparatorTextFirst =
                (x, y) -> x.toString().compareTo(y);
        Assertions.assertEquals(1, myBiComparatorTextFirst.biCompare(number, text));


    }
}
