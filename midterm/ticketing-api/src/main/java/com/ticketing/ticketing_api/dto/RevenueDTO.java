package com.ticketing.ticketing_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevenueDTO {
    private Integer event_id;
    private String event_title;
    private String event_description;
    private String event_date;
    private String event_organizer;
    private String evnet_venue;
    private Integer event_revenue;
}
