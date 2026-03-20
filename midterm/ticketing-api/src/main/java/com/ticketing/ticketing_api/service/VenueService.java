package com.ticketing.ticketing_api.service;

import com.ticketing.ticketing_api.entity.Venue;
import com.ticketing.ticketing_api.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VenueService {
    @Autowired
    private VenueRepository venueRepo;

    @Transactional
    public Venue createVenue(Venue venue) {
         return venueRepo.save(venue);
    }
}
