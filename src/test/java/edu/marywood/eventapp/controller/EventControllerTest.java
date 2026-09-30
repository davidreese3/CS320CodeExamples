package edu.marywood.eventapp.controller;

import edu.marywood.eventapp.model.Event;
import edu.marywood.eventapp.service.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EventControllerTest {

    @Test
    public void eventsDisplaysEventsFromService() {
        // Arrange
        EventService eventService = mock(EventService.class);
        EventController controller = new EventController(eventService);

        List<Event> events = List.of(
                new Event(
                        "Career Fair",
                        "October 15",
                        "Meet local employers"
                )
        );

        when(eventService.getAllEvents()).thenReturn(events);

        Model model = new ConcurrentModel();

        // Act
        String viewName = controller.displayEvents(model);

        // Assert
        assertEquals("events", viewName);
        assertEquals(events, model.getAttribute("events"));

        verify(eventService).getAllEvents();
    }
}
