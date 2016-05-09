package java8playground.streams;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.time.LocalDate.now;
import static java.util.Comparator.comparing;
import static java8playground.streams.PageCollector.toPage;

public class StreamTest {

	public static void main(String[] args) {
		List<Person> persons = createPersonList();

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

		List<Integer> numbers = Arrays.asList(1, 6, 5, 3, 4, 2, 8, 1, 5, 7, 4);
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


		Stream.iterate(0, n -> n + 1)
				.map(BigInteger::valueOf)
				.filter(i -> i.isProbablePrime(Integer.MAX_VALUE))
				.limit(10)
				.forEach(System.out::println);

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

	static class Person {
		private String firstName;
		private String lastName;
		private Integer yearOfBirth;

		public Person(String firstName, String lastName, Integer yearOfBirth) {
			this.firstName = firstName;
			this.lastName = lastName;
			this.yearOfBirth = yearOfBirth;
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
