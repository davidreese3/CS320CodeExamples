package edu.marywood.eventapp.service;

import edu.marywood.eventapp.model.Event;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventServiceTest {

    @Test
    public void addEventAddsEventToList() {
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
}
