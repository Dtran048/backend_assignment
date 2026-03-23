package com.ticketing.ticketing_api.repository;

import com.ticketing.ticketing_api.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    @Query("SELECT e FROM Event e WHERE e.status = 'UPCOMING'")
    List<Event> findUpcoming();

    @Query("SELECT e FROM Event e WHERE e.event_id = :eventId")
    List<Event> findByEventId(Integer eventId);

}
