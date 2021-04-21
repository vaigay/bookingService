package com.vaigay.reactive;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@Path("/bookings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookingController {

	@Inject
	BookingRepository bookingRepository;

	@POST()
	@Path("/add")
	public Uni<String> addBookingApi(Booking Booking) {
		return Uni.createFrom().publisher(addBooking(Booking));
	}

	private Mono<String> addBooking(Booking booking) {
		String res = setStatusBookedTicket(booking).block();
		if (res.equals("Booked Successfully")) {// check if customer can book ticket or not;
			bookingRepository.addBooking(booking);
		}
		return Mono.just(res);
	}

	private Mono<String> setStatusBookedTicket(Booking booking) {
		WebClient client = WebClient.create("http://localhost:8092");
		return client.post().uri("/matches/ticket/changeStatus/" + booking.getMatchId() + "/" + booking.getTicketId())
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON).body(Mono.just(booking), Booking.class)
				.retrieve().bodyToMono(String.class);
	}

}
