package ch.chang.java.eight.completablefuture;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CompletableFutureDemo {
    public Double getValue() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0.1D;
    }

    public int getRealyValue(int i) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 2 * i;
    }

    @Test
    public void one() {
        CompletableFuture<Double> doubleCompletableFuture = new CompletableFuture<>();
        new Thread(() -> {
            Double value = getValue();
            doubleCompletableFuture.complete(value);
        }).start();
        System.out.println("-----------------------------------");
        doubleCompletableFuture.whenComplete((v, e) -> {
            System.out.println("value: " + v);
            System.out.println("exception: ");
            e.printStackTrace();
        });
        doubleCompletableFuture.join();
    }

    @Test
    public void executor() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CompletableFuture.supplyAsync(() -> getValue(), executorService).thenApply(a -> a * 10).whenComplete((v, e) -> {
            System.out.println("value: " + v);
            System.out.println("exception: ");
            e.printStackTrace();
        });
        System.out.println("-----------------------------------");
        Thread.currentThread().join();

    }

    @Test
    public void stream() {
        List<Integer> ids = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Stream<CompletableFuture<Integer>> completableFutureStream = ids.stream().parallel().map(id -> CompletableFuture.supplyAsync(() -> getRealyValue(id), executorService));
        List<Integer> collect = completableFutureStream.map(v -> v.thenApply(i -> i * 10)).map(f -> f.join()).collect(Collectors.toList());
        Optional.ofNullable(collect).ifPresent(System.out::println);
        System.out.println("aa");
    }

    /**
     * 相对于thenApply 多了异常处理
     */
    @Test
    public void handle() {
        CompletableFuture.supplyAsync(() -> 1).handle((v, throwable) -> Integer.sum(v, 10));
    }

    /**
     * 接下在做的另外一件事，没有入参
     */
    @Test
    public void thenRun() {
        CompletableFuture.supplyAsync(() -> 1).handle((v, throwable) -> Integer.sum(v, 10)).thenRun(() -> System.out.println("end"));
    }

    /**
     * Consumer
     */
    @Test
    public void thenAccept() {
        CompletableFuture.supplyAsync(() -> 1).handle((v, throwable) -> Integer.sum(v, 10)).thenAccept(integer -> {
            System.out.println(integer);
        }).thenRun(() -> System.out.println("end"));
    }

    @Test
    public void thenAcceptBoth() {
        CompletableFuture.supplyAsync(() -> 1).handle((v, throwable) -> Integer.sum(v, 10)).thenAcceptBoth(CompletableFuture.supplyAsync(() -> 2.0), (v1, v2) -> {
            System.out.println(v1 * v2);
        }).thenRun(() -> System.out.println("end"));
    }

    @Test
    public void thenCombine() {
        CompletableFuture.supplyAsync(() -> 1).handle((v, throwable) -> Integer.sum(v, 10)).thenCombine(CompletableFuture.supplyAsync(() -> 2.0), (v1, v2) -> v1 * v2).thenAccept(System.out::println).thenRun(() -> System.out.println("end"));
    }

    @Test
    public void thenCompose() {
        CompletableFuture.supplyAsync(() -> 1).handle((v, throwable) -> Integer.sum(v, 10)).thenCompose(integer -> CompletableFuture.supplyAsync(() -> integer * 10)).thenAccept(System.out::println).thenRun(() -> System.out.println("end"));
    }

    @Test
    public void runAfterBoth() {
        CompletableFuture.supplyAsync(() -> 1).runAfterBoth(CompletableFuture.supplyAsync(() -> 2), () -> System.out.println("both end"));
    }

    @Test
    public void applyToEither() {
        CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        }).applyToEither(CompletableFuture.supplyAsync(() -> 2), integer -> integer).thenAccept(System.out::println);
    }

    @Test
    public void acceptEither() {
        CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        }).acceptEither(CompletableFuture.supplyAsync(() -> 2), System.out::println);
    }

    @Test
    public void runAfterEither() {
        CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        }).runAfterEither(CompletableFuture.supplyAsync(() -> 2), () -> System.out.println("end"));
    }

    @Test
    public void allOf() {
        List<CompletableFuture<Integer>> collect = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9).stream().map(i -> CompletableFuture.supplyAsync(() -> i * 10)).collect(Collectors.toList());
        CompletableFuture.allOf(collect.toArray(new CompletableFuture[collect.size()])).thenRun(() -> System.out.println("end"));
    }
    @Test
    public void anyOf() {
        List<CompletableFuture<Integer>> collect = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9).stream().map(i -> CompletableFuture.supplyAsync(() -> i * 10)).collect(Collectors.toList());
        CompletableFuture.anyOf(collect.toArray(new CompletableFuture[collect.size()])).thenRun(() -> System.out.println("end"));
    }

}
