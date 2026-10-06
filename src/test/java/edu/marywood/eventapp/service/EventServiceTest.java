package edu.marywood.eventapp.service;

import edu.marywood.eventapp.data.EventRepository;
import edu.marywood.eventapp.model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Lecture Notes: Mockito is now needed because EventService depends on
// EventRepository. We want to unit test the Service without requiring
// a real database.
import static org.mockito.Mockito.*;

public class EventServiceTest {

    private EventService service;

    // Lecture Notes: EventService no longer stores Events itself.
    // It now depends on an EventRepository, so our test needs to
    // provide that dependency.
    private EventRepository eventRepository;

    @BeforeEach
    void setUp() {

        // Lecture Notes: Create a mock EventRepository instead of using
        // a real repository/database. This keeps this a unit test.
        eventRepository = mock(EventRepository.class);

        // Lecture Notes: EventService now requires an EventRepository
        // through constructor injection, so we pass our mock repository
        // into the Service.
        service = new EventService(eventRepository);
    }

    @Test
    void getAllEventsReturnsEmptyListWhenNoEventsExist() {

        // Lecture Notes: The Service no longer owns an empty ArrayList.
        // We must tell the mock Repository what findAll() should return.
        when(eventRepository.findAll()).thenReturn(List.of());

        // Act
        List<Event> events = service.getAllEvents();

        // Assert
        assertTrue(events.isEmpty());

        // Lecture Notes: Verify that EventService actually delegated
        // the retrieval operation to EventRepository.
        verify(eventRepository).findAll();
    }

    @Test
    void addEventSavesEvent() {

        // Arrange
        Event event = new Event(
                "Career Fair",
                "October 15",
                "Meet local employers"
        );

        // Act
        service.addEvent(event);

        // Lecture Notes: Previously, we checked whether the Event was
        // added to the Service's ArrayList. The Service no longer owns
        // that list. Its responsibility is now to ask the Repository
        // to save the Event.
        verify(eventRepository).save(event);
    }

    @Test
    void getAllEventsReturnsStoredEvent() {

        // Arrange
        Event event = new Event(
                "Career Fair",
                "October 15",
                "Meet local employers"
        );

        // Lecture Notes: A mock does not actually store data.
        // We define what the Repository should return when findAll()
        // is called.
        when(eventRepository.findAll()).thenReturn(List.of(event));

        // Act
        List<Event> events = service.getAllEvents();

        // Assert
        assertEquals(1, events.size());
        assertEquals(event, events.get(0));

        // Lecture Notes: Verify that the Service retrieved the Events
        // through the Repository rather than managing storage itself.
        verify(eventRepository).findAll();
    }

    @Test
    void multipleEventsCanBeRetrieved() {

        // Arrange
        Event event1 = new Event(
                "Career Fair",
                "October 15",
                "Meet local employers"
        );

        Event event2 = new Event(
                "Programming Workshop",
                "October 22",
                "Introduction to Spring"
        );

        // Lecture Notes: Instead of first adding Events to an ArrayList,
        // configure the mock Repository to represent a database that
        // already contains these Events.
        when(eventRepository.findAll())
                .thenReturn(List.of(event1, event2));

        // Act
        List<Event> events = service.getAllEvents();

        // Assert
        assertEquals(2, events.size());
        assertEquals(event1, events.get(0));
        assertEquals(event2, events.get(1));

        // Lecture Notes: Verify that EventService delegated retrieval
        // to its Repository dependency.
        verify(eventRepository).findAll();
    }
}