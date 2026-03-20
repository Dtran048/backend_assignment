package com.ticketing.ticketing_api.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "tickettype")
@Data
public class TicketType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ticket_type_id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer quantity_available;

    @ManyToOne
    @JoinColumn(name="event_id")
    private Event event;

    @OneToMany(mappedBy = "tickettype")
    private List<Booking> bookings;
}
