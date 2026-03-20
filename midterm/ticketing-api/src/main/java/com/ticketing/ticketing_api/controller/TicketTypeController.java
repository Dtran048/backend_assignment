package com.ticketing.ticketing_api.controller;

import com.ticketing.ticketing_api.dto.TicketTypeDTO;
import com.ticketing.ticketing_api.entity.TicketType;
import com.ticketing.ticketing_api.service.TicketTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickettypes")
public class TicketTypeController {
    @Autowired
    private TicketTypeService ticketTypeService;

    @PostMapping
    public ResponseEntity<TicketTypeDTO> createTicketType(
        @RequestBody TicketType ticketType,
        @RequestParam Integer eventId) {
        TicketTypeDTO saved = ticketTypeService.createTicketType(ticketType, eventId);
        return ResponseEntity.status(201).body(saved);
    }
}
