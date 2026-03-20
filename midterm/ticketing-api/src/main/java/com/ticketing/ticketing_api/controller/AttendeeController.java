package com.ticketing.ticketing_api.controller;

import com.ticketing.ticketing_api.dto.AttendeeBookingsDTO;
import com.ticketing.ticketing_api.entity.Attendee;
import com.ticketing.ticketing_api.service.AttendeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendees")
public class AttendeeController {
    @Autowired
    private AttendeeService attendeeService;

    @PostMapping
    public ResponseEntity<Attendee> createAttendee(
            @RequestBody Attendee attendee) {
        Attendee saved = attendeeService.createAttendee(attendee);
        return ResponseEntity.status(201).body(saved);
    }

    @GetMapping("/{id}/bookings")
    public ResponseEntity<List<AttendeeBookingsDTO>> getAttendeeBookings(
            @PathVariable int id) {
        List<AttendeeBookingsDTO> get = attendeeService.findAttendeeBookings(id);
        return ResponseEntity.status(200).body(get);
    }
}
