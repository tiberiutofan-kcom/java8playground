package java8playground;

import com.google.common.collect.Collections2;
import org.junit.Test;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.time.LocalDate.now;
import static java.util.Collections.synchronizedList;
import static java.util.Comparator.comparing;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;
import static java8playground.streams.PageCollector.toPage;

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

	public void test() {
		persons.stream().sorted(comparing(Person::getYearOfBirth)).forEach(System.out::println);
		persons.stream().map(p -> p.getFirstName() + " " + p.getLastName())
				.forEach(System.out::println);
		persons.stream()
				.filter(p -> now().getYear() - p.getYearOfBirth() < 50)
				.count();
		persons.stream().map(Person::getYearOfBirth).distinct()
				.forEach(System.out::println);
		persons.stream().map(p -> now().getYear() - p.getYearOfBirth())
				.sorted().findFirst()
				.ifPresent(System.out::println);

//        numbers.stream().filter(i -> i % 2 != 0).forEach(System.out::println);
//        numbers.stream().filter(i -> i % 2 != 0).distinct().forEach(System.out::println);
//        numbers.stream().filter(i -> i % 2 == 0).limit(3).forEach(System.out::println);
//        numbers.stream().filter(i -> i % 2 == 0).skip(3)
		Integer pageOffset = 0;
		Integer pageSize = 0;
		persons.stream().skip(pageOffset).limit(pageSize).collect(toPage(pageOffset, pageSize, persons.size()));
//        products.stream().skip((currentPage - 1) * pageSize).limit(pageSize)
//                .forEach(System.out::println);

		List<String> words = Arrays.asList("Java", "Scala", "JavaScript", "Kotlin");
		words.stream()
				.map(word -> word.split(""))
				.flatMap(Arrays::stream)
				.distinct()
				.forEach(System.out::print);
		//outputs: JavSclriptKon


		Stream.generate(Math::random)
				.map(n -> n * 100)
				.limit(10)
				.forEach(System.out::println);

		boolean areThereAnyPersonsWithK = persons.stream()
				.map(Person::getFirstName)
				.anyMatch(n -> n.startsWith("K"));

		boolean areAllPeopleYoungerThan50 = persons.stream()
				.map(Person::getYearOfBirth)
				.allMatch(y -> now().getYear() - y < 50);
		System.out.println(areThereAnyPersonsWithK);

		Optional<Integer> findAnyPrime = numbers.stream().map(BigInteger::valueOf)
				.filter(i -> i.isProbablePrime(Integer.MAX_VALUE))
				.findFirst().map(BigInteger::intValue);
		System.out.print(findAnyPrime.get());
	}

	public Map<String, Person> indexById() {
		return persons.stream().collect(toMap(Person::getId, identity()));
	}

	public void primeNumbers() {
		Stream.iterate(0, n -> n + 1)
				.map(BigInteger::valueOf)
				.filter(i -> i.isProbablePrime(Integer.MAX_VALUE))
				.limit(10)
				.forEach(System.out::println);
	}

	public List<Integer> generateFirstPrimeNumbers(int numberOfPrimes) {
		return Stream.iterate(0, n -> n + 1)
				.map(BigInteger::valueOf)
				.filter(i -> i.isProbablePrime(Integer.MAX_VALUE))
				.limit(numberOfPrimes)
				.map(BigInteger::intValue)
				.collect(toList());
	}

	public List<Person> personsUnderAge(int age) {
		return persons.stream().filter(p -> now().getYear() - p.getYearOfBirth() < 50)
				.collect(toCollection(() -> synchronizedList(new ArrayList<>())));
	}

	public List<Person> personsStartingWithA() {
		return persons.stream().filter(p -> p.getFirstName().startsWith("A"))
				.collect(collectingAndThen(toList(), Collections::unmodifiableList));
	}

	@Test
	public void reduceSum() {
		Integer sum = numbers.stream().reduce(0, (a, b) -> a + b);
//		Integer sum = numbers.stream().reduce(0, Integer::sum);
		System.out.println(sum);
	}

	@Test
	public void reduceMax() {
		Optional<Integer> max = numbers.stream().reduce(Integer::max);
		max.ifPresent(System.out::println);
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
