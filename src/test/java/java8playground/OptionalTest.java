package java8playground;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class OptionalTest {

	public static void main(String[] args) {
		Person person = null;

		Optional.of(person)
				.map(Person::getDrivingLicence)
				.map(DrivingLicence::getCategories)
				.map(categories -> categories.contains(Category.B))
				.orElse(Boolean.FALSE);

		EventRepository eventRepository = new EventRepository();

		//eventRepository.findEvent and Event.getLocation return Optional
		String eventCity = eventRepository.findEvent("NewConcertInTown")
				.flatMap(Event::getLocation)
				.map(Location::getAddress)
				.map(Address::getCity)
				.map(City::getName)
				.orElse("TBA");
	}

	enum Category {
		A, B, C, D
	}

	static class EventRepository {

		private Map<String, Event> events;

		public Optional<Event> findEvent(String eventId) {
			return Optional.ofNullable(events.get(eventId));
		}
	}

	static class Event {

		private Location location;

		public Optional<Location> getLocation() {
			return Optional.ofNullable(location);
		}

	}

	static class Location {
		private Address address;

		public Address getAddress() {
			return address;
		}
	}

	static class Address {
		private City city;

		public City getCity() {
			return city;
		}
	}

	static class City {
		private String name;

		public String getName() {
			return name;
		}
	}

	static class Person {

		private DrivingLicence drivingLicence;

		public Person(DrivingLicence drivingLicence) {
			this.drivingLicence = drivingLicence;
		}

		public DrivingLicence getDrivingLicence() {
			return drivingLicence;
		}
	}

	static class DrivingLicence {

		private Set<Category> categories;

		public DrivingLicence(Set<Category> categories) {
			this.categories = categories;
		}

		public Set<Category> getCategories() {
			return categories;
		}
	}

}
