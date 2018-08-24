package pl.sda.intermediate11.threads;

import lombok.Synchronized;

public class Bank {

    protected static int balance = 1000;
    private static Object object1 = new Object(); //obiekty przechowujące semafor
    private static Object object2 = new Object();
    public static int operations;

    public static void withdraw(int howMuch) {
//        synchronized (object1) {
            balance = balance - howMuch;
            System.out.println(Thread.currentThread().getName() +
                    " Stan konta po wypłacie klienta : " + balance);
//        }
    }

    public static void getBack(int howMuch) {
//        synchronized (object1) {
            balance = balance + howMuch;
            System.out.println(Thread.currentThread().getName() +
                    " Stan konta po wpłacie klienta : " + balance);
            operations++;
//        }
    }

}
