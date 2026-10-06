package edu.marywood.eventapp.data;

import edu.marywood.eventapp.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {

}