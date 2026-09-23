package edu.marywood.eventapp.service;

import edu.marywood.eventapp.model.Event;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    private final List<Event> events = new ArrayList<>();

    public EventService() {
        events.add(new Event("Ciara’s Wedding",
                "October 17","Mix and mingle"
        ));

        events.add(new Event(
                "Programming Workshop",
                "October 22", "Introduction to Spring"
        ));
    }

    public List<Event> getAllEvents() {
        return events;
    }

    public void addEvent(Event event){
        events.add(event);
    }

}
