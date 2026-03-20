package com.ticketing.ticketing_api.service;

import com.ticketing.ticketing_api.dto.TicketTypeDTO;
import com.ticketing.ticketing_api.entity.Event;
import com.ticketing.ticketing_api.entity.TicketType;
import com.ticketing.ticketing_api.repository.EventRepository;
import com.ticketing.ticketing_api.repository.TicketTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TicketTypeService {
    @Autowired
    private EventRepository eventRepo;
    @Autowired
    private TicketTypeRepository ticketTypeRepo;

    @Transactional
    public TicketTypeDTO createTicketType(TicketType ticketType, Integer eventId) {
        Event event = eventRepo.findById(eventId).
                orElseThrow(() -> new RuntimeException("Event not found: " + eventId ));
        if (ticketType.getPrice() < 0  ){
            throw new RuntimeException("Invalid ticket price " );
        }
        ticketType.setEvent(event);
        ticketTypeRepo.save(ticketType);
        return new TicketTypeDTO(
                ticketType.getTicket_type_id(),
                ticketType.getName(),
                ticketType.getPrice(),
                ticketType.getQuantity_available(),
                ticketType.getEvent().getTitle()
        );
    }
}
