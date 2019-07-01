package ch.chang.java.eight.optional;

import org.junit.Test;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class OptionalDemo {

    @Test
    public void createTest() {

        Optional<Object> empty = Optional.empty();

        Optional<Integer> integer = Optional.of(1);

        //根据value 判断走empty 还是of
        Optional<Object> obj = Optional.ofNullable(null);
        obj.ifPresent(System.out::println);

    }

    @Test
    public void getTest() {
        Optional<Student> student = Optional.ofNullable(new Student(null));
        //是否存在 value != null;
        boolean present = student.isPresent();
        System.out.println(present);

        System.out.println("=========================");

        //不存在就用传递的
        Book orElse = student.map(s -> s.getBook()).orElse(new Book("a"));
        System.out.println(orElse);
        System.out.println("=========================");
        Book orElseGet = student.map(s -> s.getBook()).orElseGet(() -> new Book("b"));
        System.out.println(orElseGet);
        System.out.println("=========================");
        //不存在就抛出异常
        try {
            Book book = student.map(s -> s.getBook()).orElseThrow(RuntimeException::new);
            System.out.println(book);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("=========================");
        try {
            Book book = student.map(s -> s.getBook()).orElseThrow(() -> {
                return new RuntimeException("demo");
            });
            System.out.println(book);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("=========================");
        //过滤

        student.filter(s -> s.getBook().getName().equals("c")).ifPresent(System.out::println);
    }
    @Test
    public void  mapTest(){
        /*public<U> Optional<U> flatMap(Function<? super T, Optional<U>> mapper) {
            Objects.requireNonNull(mapper);
            if (!isPresent())
                return empty();
            else {
                return Objects.requireNonNull(mapper.apply(value));
            }
        }*/
        /*public<U> Optional<U> map(Function<? super T, ? extends U> mapper) {
            Objects.requireNonNull(mapper);
            if (!isPresent())
                return empty();
            else {
                return Optional.ofNullable(mapper.apply(value));
            }
        }*/
        /**
         * map 将结果封装为Optional
         */

    }
}