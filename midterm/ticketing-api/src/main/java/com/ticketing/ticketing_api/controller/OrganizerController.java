package com.ticketing.ticketing_api.controller;

import com.ticketing.ticketing_api.entity.Organizer;
import com.ticketing.ticketing_api.service.OrganizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/organizers")
public class OrganizerController {
    @Autowired
    private OrganizerService organizerService;

    @PostMapping
    public ResponseEntity<Organizer> createOrganizer(
            @RequestBody Organizer organizer) {
        Organizer saved = organizerService.createOrganizer(organizer);
        return ResponseEntity.status(201).body(saved);
    }
}
