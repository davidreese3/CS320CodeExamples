package edu.marywood.eventapp.controller;

import edu.marywood.eventapp.model.Event;
import edu.marywood.eventapp.service.EventService;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    // Lecture Notes: Added for validation
    @Test
    public void invalidEventIsNotSaved() throws Exception {

        // Arrange

        // Create a mock Service so no real database is needed.
        EventService eventService = mock(EventService.class);

        // Create a real Bean Validator to evaluate our
        // @NotBlank and @Size constraints.
        try (ValidatorFactory factory =
                     Validation.buildDefaultValidatorFactory()) {

            // Create a MockMvc instance for testing HTTP requests
            // against our Controller without starting a server.
            MockMvc mockMvc = MockMvcBuilders
                    .standaloneSetup(new EventController(eventService))
                    .setValidator(new SpringValidatorAdapter(
                            factory.getValidator()))
                    .build();

            // Act & Assert

            // Simulate submitting an Event with a blank name.
            mockMvc.perform(post("/events")
                            .param("name", "")
                            .param("date", "2026-11-15")
                            .param("description", "Career Fair"))

                    // The Controller returns the form (HTTP 200).
                    .andExpect(status().isOk())

                    // Verify that the form is displayed again.
                    .andExpect(view().name("event-form"))

                    // Verify that validation detected an error
                    // on the Event's name field.
                    .andExpect(model().attributeHasFieldErrors(
                            "event", "name"));

            // Verify that the Service was never called to save
            // an invalid Event.
            verify(eventService, never())
                    .addEvent(any(Event.class));
        }
    }
}
