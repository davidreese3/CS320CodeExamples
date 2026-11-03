package edu.marywood.eventapp.controller;

import edu.marywood.eventapp.model.Event;
import edu.marywood.eventapp.service.EventService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    public String showEventForm(Model model) {
        model.addAttribute("event", new Event());
        return "event-form";
    }

    // Lecture Notes: New Content Below
    @PostMapping
    public String createEvent(@Valid @ModelAttribute("event") Event event, BindingResult result) {
        if (result.hasErrors()) {
            return "event-form";
        }
        eventService.addEvent(event);
        return "redirect:/events";
    }

    @GetMapping("/{id}")
    public String displayEvent(@PathVariable Long id, Model model) {
        Event event = eventService.getEvent(id);
        model.addAttribute("event", event);
        return "event-detail";
    }
}