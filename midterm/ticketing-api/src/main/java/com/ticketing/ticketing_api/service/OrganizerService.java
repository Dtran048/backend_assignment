package com.ticketing.ticketing_api.service;

import com.ticketing.ticketing_api.entity.Organizer;
import com.ticketing.ticketing_api.repository.OrganizerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrganizerService {
    @Autowired
    private OrganizerRepository organizerRepo;

    @Transactional
    public Organizer createOrganizer(Organizer organizer) {
        return organizerRepo.save(organizer);
    }
}
