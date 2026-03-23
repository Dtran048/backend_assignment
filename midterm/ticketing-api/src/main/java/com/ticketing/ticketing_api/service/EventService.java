package com.ticketing.ticketing_api.service;

import com.ticketing.ticketing_api.dto.EventResponseDTO;
import com.ticketing.ticketing_api.dto.EventTicketTypeDTO;
import com.ticketing.ticketing_api.dto.RevenueDTO;
import com.ticketing.ticketing_api.entity.*;
import com.ticketing.ticketing_api.dto.EventDTO;
import com.ticketing.ticketing_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class EventService {
    @Autowired
    private OrganizerRepository organizerRepo;
    @Autowired
    private VenueRepository venueRepo;
    @Autowired
    private EventRepository eventRepo;
    @Autowired
    private BookingRepository bookingRepo;
    @Autowired
    private TicketTypeRepository ticketTypeRepo;

    List<String> Statuses = List.of("UPCOMING", "ONGOING", "CANCELLED", "COMPLETED");

    public List<EventDTO> findAll() {
        return eventRepo.findUpcoming().stream().map(
                event -> new EventDTO(
                        event.getEvent_id(),
                        event.getTitle(),
                        event.getDescription(),
                        event.getEvent_date(),
                        event.getOrganizer().getName(),
                        event.getVenue().getName()
                )
        ).collect(Collectors.toList());
    }

    public List<EventTicketTypeDTO> findeventbyid(Integer eventId) {
        List<Event> saved = eventRepo.findByEventId(eventId);
        List<String> tickets = ticketTypeRepo.findTicketTypesPerEvent(saved.getFirst().getEvent_id());
        return saved.stream().map(
                event -> new EventTicketTypeDTO(
                        event.getEvent_id(),
                        event.getTitle(),
                        event.getDescription(),
                        event.getEvent_date(),
                        event.getOrganizer().getName(),
                        event.getVenue().getName(),
                        tickets
                )
        ).collect(Collectors.toList());
    }

    public RevenueDTO findRevenueById(Integer eventId) {
        Event event = eventRepo.findById(eventId).
                orElseThrow(() -> new RuntimeException("Event not found: " + eventId ));
        Integer saved = bookingRepo.findRevenueForEvent(eventId);
        if  (saved == null) {
            saved = 0;
        }
        return new RevenueDTO(
                event.getEvent_id(),
                event.getTitle(),
                event.getDescription(),
                event.getEvent_date(),
                event.getOrganizer().getName(),
                event.getVenue().getName(),
                saved
        );
    }


    @Transactional
    public EventResponseDTO createEvent(Event event, Integer organizerId, Integer venueId) {
        Organizer organizer = organizerRepo.findById(organizerId)
                .orElseThrow(() -> new RuntimeException("Organizer not found: " + organizerId ));
        event.setOrganizer(organizer);
        if (!Statuses.contains(event.getStatus())  ){
            throw new RuntimeException("Incorrect status" );
        }
        Venue venue = venueRepo.findById(venueId)
                .orElseThrow(() -> new RuntimeException("Venue not found: " + venueId));
        event.setVenue(venue);
        eventRepo.save(event);
        return new EventResponseDTO(
                event.getEvent_id(),
                event.getTitle(),
                event.getDescription(),
                event.getEvent_date(),
                event.getStatus(),
                event.getOrganizer().getName(),
                event.getVenue().getName()
        );
    }
}
