package ch.chang.java.eight.optional;

import org.junit.Test;

import java.util.Optional;

public class OptionalDemo {

    @Test
    public void createTest() {

        Optional<Object> empty = Optional.empty();

        Optional<Integer> integer = Optional.of(1);

        //根据value 判断走empty 还是of
        Optional<Object> obj = Optional.ofNullable(null);

    }

    @Test
    public void getTest() {

    }
}