package edu.marywood.eventapp.service;

import edu.marywood.eventapp.data.EventRepository;
import edu.marywood.eventapp.model.Event;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    // Lecture Notes: Replace with our constructor injection
    /*
    public EventService() {
        ...
    }
    */

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    // Lecture Notes: Replace with repo logic
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public void addEvent(Event event){
        eventRepository.save(event);
    }

    // Lecture Notes: Added for a not found error
    public Event getEvent(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Event not found"));
    }

}
