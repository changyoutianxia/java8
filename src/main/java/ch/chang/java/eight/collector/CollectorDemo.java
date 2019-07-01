package ch.chang.java.eight.collector;

import ch.chang.java.eight.stream.Dish;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.DoubleBinaryOperator;
import java.util.stream.Collectors;

public class CollectorDemo {

    private final List<Apple> appleList = Arrays.asList(new Apple("yellow", 120)
            , new Apple("green", 120), new Apple("green", 150)
            , new Apple("yellow", 120), new Apple("yellow", 170));
    private final List<Dish> menu = Arrays.asList(
            new Dish("pork", false, 800.1D, Dish.Type.MEAT),
            new Dish("beef", false, 700.5D, Dish.Type.MEAT),
            new Dish("chicken", false, 400.6D, Dish.Type.MEAT),
            new Dish("french fries", true, 530.4D, Dish.Type.OTHER),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("season fruit", true, 120, Dish.Type.OTHER),
            new Dish("pizza", true, 550, Dish.Type.OTHER),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("salmon", false, 450, Dish.Type.FISH));

    @Test
    public void functionGroup() {
        Map<String, List<Apple>> listMap = new HashMap<>();
        appleList.stream().forEach(apple -> {
            List<Apple> apples = Optional.ofNullable(listMap.get(apple.getColor())).orElseGet(() -> {
                ArrayList<Apple> list = new ArrayList<>();
                listMap.put(apple.getColor(), list);
                return list;
            });
            apples.add(apple);
        });
        System.out.println(listMap.size());
        printAppleMap(listMap);
    }

    @Test
    public void Collector() {
        Map<String, List<Apple>> collect = appleList.stream().collect(Collectors.groupingBy(apple -> apple.getColor()));
        printAppleMap(collect);
    }

    private void printAppleMap(Map<String, List<Apple>> listMap) {
        Optional.ofNullable(listMap).ifPresent(appleList -> {
            appleList.forEach((s, apples) -> {
                System.out.println(s);
                System.out.println(apples);
            });
        });
    }

    @Test
    public void avgTest() {
        Optional.ofNullable(menu.stream().collect(Collectors.averagingDouble(menu -> menu.getCalories()))).ifPresent(System.out::println);
        Optional.ofNullable(menu.stream().collect(Collectors.averagingLong(menu -> (long) menu.getCalories()))).ifPresent(System.out::println);
        Optional.ofNullable(menu.stream().collect(Collectors.averagingInt(menu -> (int) menu.getCalories()))).ifPresent(System.out::println);
    }

    @Test
    public void countTest() {
        Optional.ofNullable(menu.stream().collect(Collectors.counting())).ifPresent(System.out::println);

    }

    /**
     * 处理完后继续处理
     */
    @Test
    public void thenTest() {
        Optional.ofNullable(menu.stream().filter(dish -> dish.getCalories() > 400).collect(Collectors.collectingAndThen(Collectors.averagingDouble(menu -> menu.getCalories()), result -> "avg: " + result))).ifPresent(System.out::println);
    }

    @Test
    public void groupByFunction() {
        Map<String, List<Apple>> collect = appleList.stream().collect(Collectors.groupingBy(apple -> apple.getColor()));
        printAppleMap(collect);
    }

    /**
     * 对返回结果重新封装
     */
    @Test
    public void groupByFunctionAndCollector() {
        Optional.ofNullable(appleList.stream().collect(Collectors.groupingBy(apple -> apple.getColor(), Collectors.averagingDouble(a -> a.getWeight())))).ifPresent(System.out::println);
    }


    /**
     * 指定返回类型
     */
    @Test
    public void groupByFunctionAndCollectorAndSupplier() {
        ConcurrentHashMap<String, Double> result = appleList.stream().collect(Collectors.groupingBy(apple -> apple.getColor(), ConcurrentHashMap::new, Collectors.averagingDouble(a -> a.getWeight())));
        Optional.ofNullable(result).ifPresent(System.out::println);
    }

    /**
     * 同上类似，仅仅返回结果为ConcurrentMap
     */
    @Test
    public void groupByConcurrentFunction() {
        ConcurrentMap<String, List<Apple>> collect = appleList.stream().collect(Collectors.groupingByConcurrent(apple -> apple.getColor()));
        printAppleMap(collect);
    }

    /**
     * 对返回结果重新封装
     */
    @Test
    public void groupByoncurrentFunctionAndCollector() {
        Optional.ofNullable(appleList.stream().collect(Collectors.groupingByConcurrent(apple -> apple.getColor(), Collectors.averagingDouble(a -> a.getWeight())))).ifPresent(System.out::println);
    }


    /**
     * 指定返回类型
     */
    @Test
    public void groupByoncurrentFunctionAndCollectorAndSupplier() {
        ConcurrentSkipListMap<String, Double> result = appleList.stream().collect(Collectors.groupingByConcurrent(apple -> apple.getColor(), ConcurrentSkipListMap::new, Collectors.averagingDouble(a -> a.getWeight())));
        Optional.ofNullable(result).ifPresent(System.out::println);
    }

    /**
     * join
     */
    @Test
    public void join() {
        Optional.ofNullable(menu.stream().map(menu -> menu.getName()).collect(Collectors.joining())).ifPresent(System.out::println);

        Optional.ofNullable(menu.stream().map(menu -> menu.getName()).collect(Collectors.joining(","))).ifPresent(System.out::println);

        Optional.ofNullable(menu.stream().map(menu -> menu.getName()).collect(Collectors.joining(",", "Names:[", "}"))).ifPresent(System.out::println);
    }

    @Test
    public void mapping() {
        Optional.ofNullable(menu.stream().collect(Collectors.mapping(Dish::getName, Collectors.joining(",")))).ifPresent(System.out::println);
    }

    @Test
    public void minBY() {
        menu.stream().collect(Collectors.minBy((o1, o2) -> (int) (o1.getCalories() - o2.getCalories()))).map(m -> m.getCalories()).ifPresent(System.out::println);
    }

    @Test
    public void maxBY() {
        menu.stream().collect(Collectors.maxBy((o1, o2) -> (int) (o1.getCalories() - o2.getCalories()))).map(m -> m.getCalories()).ifPresent(System.out::println);
    }

    /**
     * 根据某一个字段是否等于某一个值进行分组true,false
     */
    @Test
    public void partitioningBy() {
        Map<Boolean, List<Dish>> mapOne = menu.stream().collect(Collectors.partitioningBy(dish -> Dish.Type.FISH.equals(dish.getType())));
        Optional.ofNullable(mapOne).ifPresent(System.out::println);
        //进一步处理
        Map<Boolean, Double> mapTwo = menu.stream().collect(Collectors.partitioningBy(dish -> Dish.Type.FISH.equals(dish.getType()), Collectors.averagingDouble(Dish::getCalories)));
        Optional.ofNullable(mapTwo).ifPresent(System.out::println);
    }

    @Test
    public void reduce() {
        menu.stream().collect(Collectors.reducing((dish, dish2) ->
                dish.getCalories() > dish2.getCalories() ? dish : dish2)).ifPresent(System.out::println);

        Number collect = menu.stream().map(Dish::getCalories).collect(Collectors.reducing(0, (n1, n2) -> n1.doubleValue() + n2.doubleValue()));
        System.out.println(collect.doubleValue());
        Number collect1 = menu.stream().collect(Collectors.reducing(0, Dish::getCalories, (n1, n2) -> n1.doubleValue() + n2.doubleValue()));
        System.out.println(collect1);
    }

    /**
     * int long double
     */
    @Test
    public void summarizing() {
        DoubleSummaryStatistics doubleSummaryStatistics = menu.stream().collect(Collectors.summarizingDouble(Dish::getCalories));
        System.out.println(doubleSummaryStatistics.getMax());
    }


}
