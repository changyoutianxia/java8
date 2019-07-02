package ch.chang.java.eight.time;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class TimeDemo {
    @Test
    public void localDate() {
        LocalDate now = LocalDate.now();
        System.out.println(now.getYear());
        System.out.println(now.getMonthValue());
        System.out.println(now.getMonth());
        System.out.println(now.getDayOfYear());
        System.out.println(now.getDayOfMonth());
        System.out.println(now.getDayOfWeek());
        System.out.println(now.toString());
    }

    @Test
    public void localTime() {
        LocalTime now = LocalTime.now();
        System.out.println(now.getHour());
        System.out.println(now.getMinute());
        System.out.println(now.getSecond());
        System.out.println(now.getNano());
        System.out.println(now.toString());
    }

    @Test
    public void localDateTime() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now.getYear());
        System.out.println(now.getMonthValue());
        System.out.println(now.getDayOfMonth());
        System.out.println(now.getHour());
        System.out.println(now.getMinute());
        System.out.println(now.getSecond());
        System.out.println(now.getNano());
        System.out.println(now.toString());
    }

    @Test
    public void instant() throws InterruptedException {
        Instant now = Instant.now();
        TimeUnit.SECONDS.sleep(1);
        Instant end = Instant.now();
        Duration between = Duration.between(now, end);
        System.out.println(between.getSeconds());
    }

    @Test
    public void duration() {
        LocalTime now = LocalTime.now();
        LocalTime before = now.minusHours(1);
        Duration between = Duration.between(now, before);
        System.out.println(between.toHours());
    }

    @Test
    public void period() {
        LocalDate now = LocalDate.now();
        System.out.println(now);
        LocalDate before = now.minusYears(3).minusMonths(2).minusDays(1);
        System.out.println(before);
        Period period = Period.between(before, now);
        System.out.println(period.getYears());
        System.out.println(period.getMonths());
        System.out.println(period.getDays());
    }
    @Test
    public void format(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(now.format(dateTimeFormatter));
    }
}
