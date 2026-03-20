package com.ticketing.ticketing_api.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "event")
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer event_id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private String event_date;

    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name="organizer_id")
    private Organizer organizer;

    @ManyToOne
    @JoinColumn(name="venue_id")
    private Venue venue;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<TicketType> ticket_types;
}
















