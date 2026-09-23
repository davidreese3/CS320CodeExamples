package edu.marywood.eventapp.controller;

import edu.marywood.eventapp.model.Event;
import edu.marywood.eventapp.service.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/events")
public class EventController {
    // Lecture Notes: wait to add in constructor & service
    private final EventService eventService;

    public EventController(EventService eventService){
        this.eventService = eventService;
    }

    // Lecture Notes: don't add in model as param and .add at beginning
    @GetMapping
    public String displayEvents(Model model){
        List<Event> events = eventService.getAllEvents();
        model.addAttribute("events", events);
        return "events";
    }

    @GetMapping("/new")
    public String showEventForm() {
        return "event-form";
    }

    /* Using individual variables
    public String createEvent(
        @RequestParam String name,
        @RequestParam String date,
        @RequestParam String description) {

    // ...
    }
     */
    @PostMapping
    public String createEvent(Event event) {
        eventService.addEvent(event);
        return "redirect:/events";
    }
}