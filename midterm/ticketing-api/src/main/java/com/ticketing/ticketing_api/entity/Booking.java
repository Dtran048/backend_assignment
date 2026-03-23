package com.ticketing.ticketing_api.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "booking")
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer booking_id;

    @Column()
    private String booking_date;

    @Column()
    private String payment_status;

    @Column()
    private String booking_reference;

    @ManyToOne
    @JoinColumn(name = "attendee_id")
    private Attendee attendee;

    @ManyToOne
    @JoinColumn(name = "tickettype_id")
    private TicketType tickettype;
}
