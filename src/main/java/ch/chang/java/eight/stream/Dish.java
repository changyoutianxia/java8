package ch.chang.java.eight.stream;

public class Dish {
    private final String name;
    private final boolean vegetarian;
    private final double calories;
    private final Type type;

    public Dish(String name, boolean vegetarian, double calories, Type type) {
        this.name = name;
        this.vegetarian = vegetarian;
        this.calories = calories;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public double getCalories() {
        return calories;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "name='" + name + '\'' +
                ", vegetarian=" + vegetarian +
                ", calories=" + calories +
                ", type=" + type +
                '}';
    }

    public Type getType() {
        return type;
    }

    public enum Type {MEAT, FISH, OTHER}
}