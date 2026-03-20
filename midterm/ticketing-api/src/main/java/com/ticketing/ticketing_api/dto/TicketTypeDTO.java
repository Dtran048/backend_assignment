package com.ticketing.ticketing_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketTypeDTO {
    private Integer ticket_id;
    private String ticket_type;
    private Integer ticket_price;
    private Integer ticket_quantity;
    private String event_name;
}
