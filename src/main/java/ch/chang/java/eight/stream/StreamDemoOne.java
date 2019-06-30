package ch.chang.java.eight.stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamDemoOne {
    public static void main(String[] args) {
        List<Dish> menu = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH));
        List<String> result = menu.parallelStream().filter(currentMenu -> currentMenu.getCalories() < 500).map(Dish::getName).collect(Collectors.toList());
        result.stream().forEach(System.out::println);
    }

    @Test
    public void filter() {
        //产生Stream
        IntStream range = IntStream.range(0, 100);
        Stream<Integer> integerStream = range.boxed().filter(i -> i % 2 == 0).skip(5).limit(20);
        integerStream.forEach(System.out::println);

    }

    @Test
    public void flatMap() {
        //产生Stream
        Stream<String> stream = Arrays.asList("Hello", "Word").stream();
        //拆分为字符
        Stream<String[]> streamArray = stream.map(s -> s.split(""));
        //拼接为一个Stream
        Stream<String> stringStream = streamArray.flatMap(Arrays::stream);
        //去重
        Stream<String> distinct = stringStream.distinct();
        distinct.forEach(System.out::println);
    }

    @Test
    public void matched() {
        Stream<Integer> stream = Arrays.stream(new Integer[]{0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 20});
        System.out.println("------------------------------");
        boolean b = stream.allMatch(i -> i > 10);
        System.out.println(b);

        System.out.println("------------------------------");
        stream = Arrays.stream(new Integer[]{0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 20});
        boolean noneMatch = stream.noneMatch(i -> i < 0);
        System.out.println(noneMatch);

        System.out.println("------------------------------");
        stream = Arrays.stream(new Integer[]{0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 20});
        boolean anyMatch = stream.anyMatch(i -> i > 10);
        System.out.println(anyMatch);

    }

    @Test
    public void reduce() {
        Stream<Integer> stream = Arrays.stream(new Integer[]{0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 20});
        Integer reduce = stream.reduce(0, (a, b) -> a + b);
        System.out.println(reduce);

        stream = Arrays.stream(new Integer[]{0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 20});
        reduce = stream.reduce(0, Integer::sum);
        System.out.println(reduce);

        stream = Arrays.stream(new Integer[]{0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 20});
        reduce = stream.reduce(Integer::min).orElse(-1);
        System.out.println(reduce);

        stream = Arrays.stream(new Integer[]{0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 20});
        Optional<Integer> optionalInteger = stream.reduce(Integer::max);
        optionalInteger.ifPresent(System.out::println);
    }
}
