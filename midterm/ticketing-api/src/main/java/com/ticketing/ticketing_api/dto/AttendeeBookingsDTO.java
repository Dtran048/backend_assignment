package com.ticketing.ticketing_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendeeBookingsDTO {
    private String attendee_name;
    private String attendee_email;
    private String booking_date;
    private String payment_status;
    private String reference_number;
    private String event_title;

}
