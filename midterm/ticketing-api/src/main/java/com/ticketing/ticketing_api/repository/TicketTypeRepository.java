package com.ticketing.ticketing_api.repository;

import com.ticketing.ticketing_api.entity.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketTypeRepository extends JpaRepository<TicketType, Integer> {
    @Query("Select t.name FROM TicketType t WHERE t.event.event_id =  :eventId")
    List<String> findTicketTypesPerEvent(Integer eventId);
}
