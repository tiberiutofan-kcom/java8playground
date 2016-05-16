package java8playground;

import java8playground.streams.Page;
import org.junit.Test;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.time.LocalDate.now;
import static java.util.Collections.synchronizedList;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;
import static java8playground.streams.PageCollector.toPage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StreamTest {

    private List<Integer> numbers = Arrays.asList(1, 6, 5, 3, 4, 2, 8, 1, 5, 7, 4);
    private List<Person> persons = createPersonList();

    private static List<Person> createPersonList() {
        return Arrays.asList(
                new Person("Robert", "Martin", 1952),
                new Person("Martin", "Fowler", 1963),
                new Person("James", "Gosling", 1955),
                new Person("Brendan", "Eich", 1961),
                new Person("Kent", "Beck", 1961),
                new Person("Joshua", "Bloch", 1961)
        );
    }

    @Test
    public void anyMatch() {
        boolean areThereAnyPersonsWithK = persons.stream()
                .map(Person::getFirstName)
                .anyMatch(n -> n.startsWith("K"));

        assertTrue(areThereAnyPersonsWithK);
    }

    @Test
    public void allMatch() {
        boolean areAllPeopleOlderThan50 = persons.stream()
                .map(Person::getYearOfBirth)
                .allMatch(y -> now().getYear() - y > 50);

        assertTrue(areAllPeopleOlderThan50);
    }

    @Test
    public void infiniteRandom() {
        Stream.generate(Math::random)
                .map(n -> n * 100)
                .map(Double::intValue)
                .limit(10)
                .forEach(System.out::println);
    }

    @Test
    public void parallel() {
        List<String> words = Arrays.asList("Java", "Scala", "JavaScript", "Kotlin");

        words.stream().parallel()
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .forEach(System.out::print);
    }

    @Test
    public void pagination() {
        Page<Person> personPage = persons.stream().skip(1).limit(4).collect(toPage(1, 4, persons.size()));

        assertEquals(4, personPage.getCurrentPage().size());
    }

    @Test
    public void countUnder55() {
        assertEquals(1,
                persons.stream()
                        .filter(p -> now().getYear() - p.getYearOfBirth() < 55)
                        .count());
    }

    @Test
    public void sortByYearOfBirth() {
        persons.stream().sorted(comparing(Person::getYearOfBirth)).forEach(System.out::println);
    }

    @Test
    public void indexById() {
        Map<String, String> map = persons.stream().collect(toMap(Person::getId, Person::getFirstName));
        System.out.println(map.getClass());
    }

    @Test
    public void primeNumbers() {
        Stream.iterate(0, n -> n + 1).map(BigInteger::valueOf).filter(i -> {
            System.out.println();
            return i.isProbablePrime(Integer.MAX_VALUE);
        }).limit(10).forEach(System.out::println);
    }

    @Test
    public void generateFirstPrimeNumbers() {
        List<Integer> accumulator =
                Stream.iterate(0, n -> n + 1).parallel()
                        .map(BigInteger::valueOf)
                        .filter(i -> i.isProbablePrime(Integer.MAX_VALUE))
                        .limit(100_000)
                        .map(BigInteger::intValue)
                        .collect(Collectors.toList());

        assertEquals(100_000, accumulator.size());
    }

    @Test
    public void personsUnderAge() {
        List<Person> personsUnder50 = persons.stream().filter(p -> now().getYear() - p.getYearOfBirth() < 50)
                .collect(toCollection(() -> synchronizedList(new ArrayList<>())));

        personsUnder50.forEach(System.out::println);
    }

    @Test
    public void personsStartingWithA() {
        List<String> personsWithA = persons.stream()
                .map(Person::getFirstName)
                .map(Optional::ofNullable)
                .map(n -> n.orElse("Kappa"))
                .filter(n -> n.startsWith("K"))
                .collect(collectingAndThen(toList(), Collections::unmodifiableList));

        System.out.println(personsWithA);
    }

    @Test
    public void reduceSum() {
//        Integer sum = numbers.stream().reduce(0, (a, b) -> a + b);
        Integer sum = numbers.stream().reduce(0, Integer::sum);
        System.out.println(sum);
    }

    @Test
    public void reduceMax() {
        Optional<Integer> max = numbers.stream().reduce(Integer::max);
        max.ifPresent(System.out::println);
    }

    @Test
    public void countByYearOfBirth() {
        Map<Integer, Long> countByYear = persons.stream().sorted(comparing(Person::getYearOfBirth)).collect(groupingBy(Person::getYearOfBirth, counting()));

        countByYear.forEach((year, count) -> System.out.println(year + " " + count));
    }

    static class Person {
        private String id = UUID.randomUUID().toString();
        private String firstName;
        private String lastName;
        private Integer yearOfBirth;

        public Person(String firstName, String lastName, Integer yearOfBirth) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.yearOfBirth = yearOfBirth;
        }

        public String getId() {
            return id;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public Integer getYearOfBirth() {
            return yearOfBirth;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", yearOfBirth=" + yearOfBirth +
                    '}';
        }
    }

}
