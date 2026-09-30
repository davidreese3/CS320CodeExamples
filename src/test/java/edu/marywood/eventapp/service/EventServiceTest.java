package edu.marywood.eventapp.service;

import edu.marywood.eventapp.model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventServiceTest {

    @Test
    public void addEventAddsEventToListOriginal() {
        EventService service = new EventService();
        Event event = new Event(
                "Career Fair",
                "October 15",
                "Meet local employers"
        );
        service.addEvent(event);
        // we init 2 example events
        assertEquals(3, service.getAllEvents().size());
        // Lecture Notes: for our failing test
        // assertEquals(2, service.getAllEvents().size());
    }

    // Lecture Notes: remove this first test when implementing the following tests

    private EventService service;

    @BeforeEach
    void setUp() {
        service = new EventService();
    }

    @Test
    void getAllEventsReturnsEmptyListWhenNoEventsExist() {
        // Act
        List<Event> events = service.getAllEvents();
        // Assert
        assertTrue(events.isEmpty());
    }

    @Test
    void addEventAddsEventToList() {
        // Arrange
        Event event = new Event(
                "Career Fair",
                "October 15",
                "Meet local employers"
        );
        // Act
        service.addEvent(event);
        // Assert
        assertEquals(1, service.getAllEvents().size());
    }

    @Test
    void addedEventCanBeRetrieved() {
        // Arrange
        Event event = new Event(
                "Career Fair",
                "October 15",
                "Meet local employers"
        );
        // Act
        service.addEvent(event);
        List<Event> events = service.getAllEvents();
        // Assert
        assertEquals(event, events.get(0));
    }

    @Test
    void multipleEventsCanBeAdded() {
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
        // Act
        service.addEvent(event1);
        service.addEvent(event2);
        // Assert
        assertEquals(2, service.getAllEvents().size());
    }

    @Test
    void multipleAddedEventsCanBeRetrieved() {
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
        // Act
        service.addEvent(event1);
        service.addEvent(event2);
        List<Event> events = service.getAllEvents();
        // Assert
        assertEquals(event1, events.get(0));
        assertEquals(event2, events.get(1));
    }
}
