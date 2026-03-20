package com.ticketing.ticketing_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponseDTO {
    private Integer booking_id;
    private String booking_reference;
    private String booking_date;
    private String payment_status;
    private String attendee_name;
    private String ticket_type;
}
