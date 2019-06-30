package ch.chang.java.eight.lanmuda;

import java.util.ArrayList;
import java.util.List;

public class LanmudaDeonOne {
    static class Apple {
        private String color;
        private Integer weight;

        public Apple(String color, Integer weight) {
            this.color = color;
            this.weight = weight;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public Integer getWeight() {
            return weight;
        }

        public void setWeight(Integer weight) {
            this.weight = weight;
        }
    }

    @FunctionalInterface
    interface AppleFilter {
        boolean filter(Apple apple);
    }

    public static void main(String[] args) {
        List<Apple> apples = new ArrayList<>();
        apples.add(new Apple("green", 160));
        apples.add(new Apple("red", 150));
      /*  List<Apple> filter = filter(apples, new AppleFilter() {
            @Override
            public boolean filter(Apple apple) {
                return "green".equals(apple.getColor());
            }
        });
        System.out.println(filter.size());*/
        List<Apple> result = filter(apples, (apple ->
                "green".equalsIgnoreCase(apple.getColor())
        ));
        assert 1 == result.size();
    }

    public static List<Apple> filter(List<Apple> apples, AppleFilter appleFilter) {
        List<Apple> result = new ArrayList<>();
        for (Apple currentApple : apples) {
            if (appleFilter.filter(currentApple)) {
                result.add(currentApple);
            }
        }
        return result;
    }

}
