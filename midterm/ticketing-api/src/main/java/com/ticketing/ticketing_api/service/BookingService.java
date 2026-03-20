package com.ticketing.ticketing_api.service;

import com.ticketing.ticketing_api.dto.BookingResponseDTO;
import com.ticketing.ticketing_api.entity.Attendee;
import com.ticketing.ticketing_api.entity.Booking;
import com.ticketing.ticketing_api.entity.TicketType;
import com.ticketing.ticketing_api.repository.AttendeeRepository;
import com.ticketing.ticketing_api.repository.BookingRepository;
import com.ticketing.ticketing_api.repository.TicketTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;

@Service
public class BookingService {
    @Autowired
    private TicketTypeRepository ticketTypeRepo;

    @Autowired
    private AttendeeRepository attendeeRepo;

    @Autowired
    private BookingRepository bookingRepo;

    List<String> PayStatus = List.of("PENDING", "CONFIRMED", "CANCELLED");

    @Transactional
    public BookingResponseDTO cancelbooking(@PathVariable Integer id) {
        Booking booking = bookingRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found: " + id ));
        if (booking.getPayment_status().equals("CANCELLED")){
            throw new RuntimeException("Booking already cancelled ");
        }
        booking.setPayment_status("CANCELLED");
        TicketType ticketType = booking.getTickettype();
        ticketType.setQuantity_available(ticketType.getQuantity_available() + 1);
        bookingRepo.save(booking);
        return new BookingResponseDTO(
                booking.getBooking_id(),
                booking.getBooking_reference(),
                booking.getBooking_date(),
                booking.getPayment_status(),
                booking.getAttendee().getName(),
                booking.getTickettype().getName()
        );
    }

    @Transactional
    public BookingResponseDTO createBooking(Integer attendeeId, Integer ticketTypeId) {
        Attendee attendee = attendeeRepo.findById(attendeeId)
                .orElseThrow(() -> new RuntimeException("Attendee not found: " + attendeeId ));
        TicketType ticketType = ticketTypeRepo.findById(ticketTypeId)
                .orElseThrow(() -> new RuntimeException("Ticket Type not found: " + ticketTypeId ));
        if(!bookingRepo.findExisiting(attendeeId, ticketTypeId).isEmpty()){
            throw new RuntimeException("You have already booked this ticket type");
        }else if (ticketType.getQuantity_available() >0){
            ticketType.setQuantity_available(ticketType.getQuantity_available() - 1);
        } else{
            throw new RuntimeException("Sorry, this ticket type is sold out.");
        }
        Booking booking = new Booking();
        booking.setAttendee(attendee);
        booking.setTickettype(ticketType);
        booking.setPayment_status("CONFIRMED");
        String year = String.valueOf(Year.now().getValue());
        booking.setBooking_date((LocalDate.now()).toString());
        bookingRepo.save(booking);
        booking.setBooking_reference("TKT-" + year + "-" + String.format("%05d", booking.getBooking_id()));
        return new BookingResponseDTO(
                booking.getBooking_id(),
                booking.getBooking_reference(),
                booking.getBooking_date(),
                booking.getPayment_status(),
                booking.getAttendee().getName(),
                booking.getTickettype().getName()
        );
    }

}
