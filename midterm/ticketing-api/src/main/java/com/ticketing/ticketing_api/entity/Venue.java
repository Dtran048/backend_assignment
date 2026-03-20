package com.ticketing.ticketing_api.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "venue")
@Data
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer venue_id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private Integer total_capacity;

    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL)
    private List<Event> events;
}
