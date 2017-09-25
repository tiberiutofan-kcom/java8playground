package java8playground.optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

public class OptionalByDesignTest {

    public static final String EVENT_WITHOUT_LOCATION = "NewConcertInTown";
    public static final String MISSING_EVENT = "NoEvent";
    public static final String EVENT_IN_BUCHAREST = "EventInBucharest";
    private EventRepository eventRepository;

    @Before
    public void setup() {
        eventRepository = Mockito.mock(EventRepository.class);
        when(eventRepository.findEvent(anyString())).thenReturn(Optional.empty());
        when(eventRepository.findEvent(eq(EVENT_WITHOUT_LOCATION))).thenReturn(Optional.of(new Event()));
        when(eventRepository.findEvent(eq(EVENT_IN_BUCHAREST)))
                .thenReturn(Optional.of(new Event(new Location(new Address(new City(Locations.BUCHAREST))))));
    }

    @Test
    public void noEvent() throws Exception {
        assertEquals(Locations.TBA, getEventCity(MISSING_EVENT));
    }

    @Test
    public void noLocation() throws Exception {
        assertEquals(Locations.TBA, getEventCity(EVENT_WITHOUT_LOCATION));
    }

    @Test
    public void validLocation() throws Exception {
        assertEquals(Locations.BUCHAREST, getEventCity(EVENT_IN_BUCHAREST));
    }

    private String getEventCity(String eventId) {
        return eventRepository.findEvent(eventId)
                .flatMap(Event::getLocation)
                .map(Location::getAddress)
                .map(Address::getCity)
                .map(City::getName)
                .orElse(Locations.TBA);
    }

    public static class EventRepository {

        private Map<String, Event> events = new HashMap<>();

        public Optional<Event> findEvent(String eventId) {
            return Optional.ofNullable(events.get(eventId));
        }
    }

    public static class Event {

        private Location location;

        public Event(Location location) {
            this.location = location;
        }

        public Event() {
        }

        public Optional<Location> getLocation() {
            return Optional.ofNullable(location);
        }

    }

    public static class Location {
        private Address address;

        public Location(Address address) {
            this.address = address;
        }

        public Address getAddress() {
            return address;
        }
    }

    public static class Address {
        private City city;

        public Address(City city) {
            this.city = city;
        }

        public City getCity() {
            return city;
        }
    }

    public static class City {
        private String name;

        public City(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    class Locations {
        public static final String TBA = "TBA";
        public static final String BUCHAREST = "Bucharest";
    }

}
