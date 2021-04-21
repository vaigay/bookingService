package com.vaigay.reactive;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    private String matchId;
    private String ticketId;
    private String customerId;

}
