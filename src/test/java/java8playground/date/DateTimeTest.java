package java8playground.date;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateTimeTest {

    @Test
    public void localDateTime() throws Exception {

        LocalDate presentationDay = LocalDate.of(2016, 5, 10);
        System.out.println("Presentation date: " + presentationDay);
        //2016-05-10

        LocalTime presentationTime = LocalTime.of(18, 0);
        System.out.println("Presentation time: " + presentationTime);
        //18:00

        LocalDateTime presentationDateTime = presentationDay.atTime(presentationTime);
        System.out.println("Presentation time: " + presentationDateTime);
        //2016-05-10T18:00

        LocalDate today = LocalDate.now();
        System.out.println("Today is: " + today);
        //2016-05-10

        if (today.isBefore(presentationDay)) {
            System.out.println("There's still time");
        }
    }

    @Test
    public void instant() throws Exception {
        Instant now = Instant.ofEpochMilli(System.currentTimeMillis());
        Instant.now();
        System.out.println(now + " " + Instant.now());
        //2016-05-10T07:03:47.399Z

    }

    @Test
    public void duration() throws Exception {
        Duration _7Seconds = Duration.ofSeconds(7);
        System.out.println(_7Seconds);
        //PT7S

        Duration _28DaysLater = _7Seconds.plusDays(28);
        System.out.println(_28DaysLater);
        //PT672H7Sd

        Duration timeLeft = Duration.between(LocalDateTime.now(),
                LocalDateTime.of(2016, 5, 10, 18, 0));
        System.out.println(timeLeft);
        //PT-7H-59M-6.426S
    }

    @Test
    public void period() throws Exception {
        Period oneYear = Period.ofYears(1);
        System.out.println(oneYear);
        //P1Y

        Period _28DaysLater = oneYear.plusDays(28);
        System.out.println(_28DaysLater);
        //P1Y28D

        Period periodLeft = Period.between(LocalDate.now(),
                LocalDate.of(2016, 5, 10)); //P0D
        System.out.println(periodLeft);
        //P0D
    }

    @Test
    public void zonedDate() throws Exception {
        ZoneId londonZone = ZoneId.of("Europe/London");
        System.out.println(londonZone);
        ZonedDateTime zonedDateTime = LocalDateTime.now().atZone(ZoneId.systemDefault());
        //2016-05-10T10:51:36.901
        ZonedDateTime dateInLondon = zonedDateTime.withZoneSameInstant(londonZone);
        //2016-05-10T08:51:36.915+01:00[Europe/London]
        System.out.println(dateInLondon);
    }
}
