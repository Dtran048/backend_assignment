package com.ticketing.ticketing_api.controller;

import com.ticketing.ticketing_api.dto.EventDTO;
import com.ticketing.ticketing_api.dto.EventResponseDTO;
import com.ticketing.ticketing_api.dto.EventTicketTypeDTO;
import com.ticketing.ticketing_api.dto.RevenueDTO;
import com.ticketing.ticketing_api.service.EventService;
import com.ticketing.ticketing_api.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping
    public ResponseEntity<List<EventDTO>> findUpcoming() {
        List<EventDTO> get = eventService.findAll();
        return ResponseEntity.status(200).body(get);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<EventTicketTypeDTO>> findTicketTypes(@PathVariable Integer id) {
        List<EventTicketTypeDTO> get = eventService.findeventbyid(id);
        return ResponseEntity.status(200).body(get);
    }

    @GetMapping("/{id}/revenue")
    public ResponseEntity<RevenueDTO> getRevenue(@PathVariable Integer id) {
        RevenueDTO get = eventService.findRevenueById(id);
        return ResponseEntity.status(200).body(get);
    }

    @PostMapping
    public ResponseEntity<EventResponseDTO> createEvent(
                @RequestBody Event event,
                @RequestParam Integer organizerId,
                @RequestParam Integer venueId) {
        EventResponseDTO saved = eventService.createEvent(event, organizerId, venueId);
        return ResponseEntity.status(201).body(saved);
    }
}

