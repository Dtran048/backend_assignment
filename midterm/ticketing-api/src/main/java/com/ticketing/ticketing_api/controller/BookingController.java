package com.ticketing.ticketing_api.controller;

import com.ticketing.ticketing_api.dto.BookingResponseDTO;
import com.ticketing.ticketing_api.entity.Booking;
import com.ticketing.ticketing_api.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PutMapping("/{id}/cancel")
    public ResponseEntity<BookingResponseDTO> cancelBooking(
            @PathVariable Integer id) {
        BookingResponseDTO put = bookingService.cancelbooking(id);
        return ResponseEntity.status(200).body(put);
    }

    @PostMapping
    public ResponseEntity<BookingResponseDTO> createBooking(
            @RequestParam Integer attendeeId,
            @RequestParam Integer ticketTypeId){
        BookingResponseDTO saved = bookingService.createBooking(attendeeId, ticketTypeId);
        return ResponseEntity.status(201).body(saved);
    }
}
