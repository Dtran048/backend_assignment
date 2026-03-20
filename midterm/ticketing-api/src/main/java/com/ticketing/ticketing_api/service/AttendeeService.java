package com.ticketing.ticketing_api.service;

import com.ticketing.ticketing_api.dto.AttendeeBookingsDTO;
import com.ticketing.ticketing_api.entity.Attendee;
import com.ticketing.ticketing_api.repository.AttendeeRepository;
import com.ticketing.ticketing_api.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendeeService {
    @Autowired
    private AttendeeRepository attendeeRepo;

    @Autowired
    private BookingRepository bookingRepo;

    @Transactional
    public Attendee createAttendee(Attendee attendee) {
        return attendeeRepo.save(attendee);
    }

    public List<AttendeeBookingsDTO> findAttendeeBookings(Integer attendeeId) {
        return bookingRepo.findAttendeeBookings(attendeeId).stream().map(
                 booking -> new AttendeeBookingsDTO(
                         booking.getAttendee().getName(),
                         booking.getAttendee().getEmail(),
                         booking.getBooking_date(),
                         booking.getPayment_status(),
                         booking.getBooking_reference(),
                         booking.getTickettype().getEvent().getTitle()
                )
        ).collect(Collectors.toList());
    }
}
