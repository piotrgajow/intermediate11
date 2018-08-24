package pl.sda.intermediate11.threads;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;

public class CompletableFuturesTest {


    @Test
    void oneByOne() {
        Long aLong = downloadAdditionalInfo();
        String s = downloadDescription();
        String s1 = downloadPhotos();
        BigDecimal bigDecimal = downloadPrice();
    }

    @Test
    void inThreads() {
        Thread thread1 = new Thread(() -> transformToString(downloadAdditionalInfo(), a -> a.toString()));
        Thread thread2 = new Thread(() -> transformToString(downloadDescription(), a -> a));
        Thread thread3 = new Thread(() -> transformToString(downloadPhotos(), a -> a));
        Thread thread4 = new Thread(() -> transformToString(downloadPrice(), a -> a.toPlainString()));

        List<Thread> threads = Lists.newArrayList(thread1, thread2, thread3, thread4);
        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void futures() {
        ExecutorService executorService = Executors.newFixedThreadPool(8);

        Future<String> descrFuture = executorService.submit(() -> downloadDescription());
        Future<String> photosFuture = executorService.submit(() -> downloadPhotos());
        Future<BigDecimal> priceFuture = executorService.submit(() -> downloadPrice());
        Future<Long> addInfoFuture = executorService.submit(() -> downloadAdditionalInfo());


        executorService.submit(() -> transformToString( descrFuture.get(),a -> a));
        executorService.submit(() -> transformToString( photosFuture.get(),a -> a));
        executorService.submit(() -> transformToString( addInfoFuture.get(),a -> a.toString()));
        executorService.submit(() -> transformToString( priceFuture.get(),a -> a.toPlainString()));

        executorService.shutdown();
        while (!executorService.isTerminated()) {
        }
    }

    @Test
    void completableFutures() {
        CompletableFuture<String> descrCF = CompletableFuture.supplyAsync(() -> downloadDescription());
        CompletableFuture<String> descr2CF = CompletableFuture.supplyAsync(() -> downloadDescription2());
        CompletableFuture<String> photosCF = CompletableFuture.supplyAsync(() -> downloadPhotos());
        CompletableFuture<BigDecimal> priceCF = CompletableFuture.supplyAsync(() -> downloadPrice());
        CompletableFuture<Long> addInfoCF = CompletableFuture.supplyAsync(() -> downloadAdditionalInfo());

        CompletableFuture<String> descrResultCF =
                descrCF.applyToEitherAsync(descr2CF, e -> {
                    System.out.println("ściągniął się" + e);
                    return e;
                }).thenApplyAsync(a->a);
        CompletableFuture<String> photosResultCF = photosCF.thenApplyAsync(a->a);
        CompletableFuture<String> priceResultCF = priceCF.thenApplyAsync(a->a.toPlainString());
        CompletableFuture<String> additionalInfoResultCF = addInfoCF.thenApplyAsync(a->a.toString());

        descrResultCF.join();
        photosResultCF.join();
        priceResultCF.join();
        additionalInfoResultCF.join();
    }

    private <T> String transformToString(T value, Function<T, String> function) {
        simulateDelay(4500);
        return function.apply(value);
    }


    private String downloadDescription() {
        System.out.println(Thread.currentThread().getName() + " Descr loading");
        simulateDelay(4000);
        System.out.println(Thread.currentThread().getName() + " Descr loaded!!!!");
        return "Opis";
    }

    private String downloadDescription2() {
        System.out.println(Thread.currentThread().getName() + " Descr 2 loading");
        simulateDelay(4100);
        System.out.println(Thread.currentThread().getName() + " Descr 2 loaded!!!!");
        return "Opis 2";
    }

    private BigDecimal downloadPrice() {
        System.out.println(Thread.currentThread().getName() + " Price loading");
        simulateDelay(4500);
        System.out.println(Thread.currentThread().getName() + " Price loaded!!!!!");
        return BigDecimal.valueOf(2.3);
    }

    private String downloadPhotos() {
        System.out.println(Thread.currentThread().getName() + " Photos loading");
        simulateDelay(3000);
        System.out.println(Thread.currentThread().getName() + " Photos loaded!!!!!");
        return "Zdjecia";
    }

    private Long downloadAdditionalInfo() {
        System.out.println(Thread.currentThread().getName() + " AdditionalInfo loading");
        simulateDelay(3500);
        System.out.println(Thread.currentThread().getName() + " AdditionalInfo loaded!!!!!");
        return 123L;
    }

    private void simulateDelay(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @ToString
    @AllArgsConstructor
    @Getter
    private class ProductForTest {
        private String description;
        private String price;
        private String photo;
        private String additionalInfo;
    }


}
