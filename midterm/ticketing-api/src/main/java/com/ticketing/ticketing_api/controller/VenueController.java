package com.ticketing.ticketing_api.controller;

import com.ticketing.ticketing_api.entity.Venue;
import com.ticketing.ticketing_api.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/venues")
public class VenueController {
    @Autowired
    private VenueService venueService;

    @PostMapping
    public ResponseEntity<Venue> createVenue(
            @RequestBody Venue venue) {
        Venue saved = venueService.createVenue(venue);
        return ResponseEntity.status(201).body(saved);
    }
}
