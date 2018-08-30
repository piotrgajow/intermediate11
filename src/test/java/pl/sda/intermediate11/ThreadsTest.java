package pl.sda.intermediate11;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

public class ThreadsTest {

    @Test
    void threads() throws InterruptedException {

        Thread customProcessThread = new CustomProcessThread(); //w ten sposób od razu twrzymy wątek
        customProcessThread.start();

        Thread thread = new Thread(new CustomProcessRunnable()); //w przypadku runnable musimy jeszcze utworzyć wątek
        thread.start();

        customProcessThread.join();
        thread.join();
    }
}
