package com.ticketing.ticketing_api.repository;

import com.ticketing.ticketing_api.entity.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendeeRepository extends JpaRepository<Attendee, Integer> {
}
