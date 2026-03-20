package com.ticketing.ticketing_api.repository;

import com.ticketing.ticketing_api.entity.Booking;
import com.ticketing.ticketing_api.entity.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    @Query("Select SUM(b.tickettype.price) From Booking b WHERE b.tickettype.event.event_id = :eventId AND b.payment_status = 'CANCELLED'")
    Integer findRevenueForEvent(Integer eventId);

    @Query("Select b FROM Booking b WHERE b.attendee.attendee_id = :attendeeId ")
    List<Booking> findAttendeeBookings(Integer attendeeId);

    @Query("Select b FROM Booking b WHERE b.attendee.attendee_id = :attendeeId AND b.tickettype.ticket_type_id = :ticketTypeId AND b.payment_status = 'CONFIRMED'")
    List<Booking> findExisiting(Integer attendeeId, Integer ticketTypeId);
}