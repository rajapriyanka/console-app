package flight.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import flight.entity.Booking;
import flight.entity.Flight;
import flight.entity.Passenger;
import flight.entity.Trip;
import flight.util.SeatClass;
import flight.util.TripStatus;

public class BookingService {

	public final Map<Passenger, List<Booking>> bookingDb = new HashMap<>();

	private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

	public Map<Passenger, List<Booking>> getBookingDb() {
		return bookingDb;
	}

	public void cancelBookingsByTrip(Trip trip) {

		for (Map.Entry<Passenger, List<Booking>> entry : bookingDb.entrySet()) {

			Passenger passenger = entry.getKey();
			List<Booking> bookings = entry.getValue();

			for (Booking booking : bookings) {

				if (booking.getTrip().equals(trip) && !booking.isCancelled()) {

					Flight flight = booking.getFlight();

					for (int i = 0; i < booking.getTicketCount(); i++) {
						if (booking.getSeatClass() == SeatClass.ECONOMY) {
							flight.cancelEconomySeat();
						} else {
							flight.cancelBusinessSeat();
						}
					}

					passenger.setWallet(passenger.getWallet() + booking.getTotalFare());

					booking.cancel();
				}
			}
		}
	}
	public void restoreSeatsByTrip(Trip trip, Flight flight) {

	    for (Map.Entry<Passenger, List<Booking>> entry : bookingDb.entrySet()) {
	        for (Booking booking : entry.getValue()) {

	            if (booking.getTrip().equals(trip)) {

	                int seats = booking.getTicketCount();

	                if (booking.getSeatClass() == SeatClass.ECONOMY) {
	                    flight.setAvailableEconomySeats(
	                        flight.getAvailableEconomySeats() + seats
	                    );
	                } else {
	                    flight.setAvailableBusinessSeats(
	                        flight.getAvailableBusinessSeats() + seats
	                    );
	                }
	            }
	        }
	    }
	}

	public Map<Integer, Trip> searchFlightsByRoute(Map<String, Flight> flightDb, String from, String to) {

		Map<Integer, Trip> availableRoutes = new HashMap<>();
		int option = 1;

		for (Flight flight : flightDb.values()) {
			for (Trip route : flight.getRoute()) {

				boolean isSameRoute = route.getFrom().equalsIgnoreCase(from) && route.getTo().equalsIgnoreCase(to);

				boolean isBookableStatus = route.getStatus() == TripStatus.YET_TO_DEPART;

				if (isSameRoute && isBookableStatus) {

					System.out.println(
							option + ". Flight: " + flight.getFlightName() + " | Flight No: " + flight.getFlightNo()
									+ " | Departure: " + route.getDepartureTime().format(DATE_TIME_FORMAT)
									+ " | Arrival: " + route.getArrivalTime().format(DATE_TIME_FORMAT)
									+ " | Economy Seats: " + flight.getAvailableEconomySeats() + " | Business Seats: "
									+ flight.getAvailableBusinessSeats());

					availableRoutes.put(option++, route);
				}
			}
		}

		if (availableRoutes.isEmpty()) {
			throw new IllegalStateException("No available flights for this route");
		}

		return availableRoutes;
	}

	public void bookTicket(Passenger passenger, Flight flight, Trip route, SeatClass seatClass, int ticketCount) {

		if (ticketCount <= 0) {
			throw new IllegalArgumentException("Ticket count must be greater than 0");
		}

		double farePerTicket;
		int availableSeats;

		if (seatClass == SeatClass.ECONOMY) {
			farePerTicket = route.getEconomyFare();
			availableSeats = flight.getAvailableEconomySeats();
		} else if (seatClass == SeatClass.BUSINESS) {
			farePerTicket = route.getBusinessFare();
			availableSeats = flight.getAvailableBusinessSeats();
		} else {
			throw new IllegalStateException("Invalid seat class");
		}

		if (availableSeats < ticketCount) {
			throw new IllegalStateException("Not enough seats available");
		}

		double totalFare = farePerTicket * ticketCount;

		if (passenger.getWallet() < totalFare) {
			throw new IllegalStateException("Insufficient wallet balance");
		}

		for (int i = 0; i < ticketCount; i++) {
			if (seatClass == SeatClass.ECONOMY) {
				flight.reserveEconomySeat();
			} else {
				flight.reserveBusinessSeat();
			}
		}

		passenger.setWallet(passenger.getWallet() - totalFare);

		Booking booking = new Booking(flight, route, seatClass, ticketCount, totalFare);
		bookingDb.computeIfAbsent(passenger, k -> new ArrayList<>()).add(booking);

		System.out.println("Booking successful");
		System.out.println("Booking ID: " + booking.getBookingId());
		System.out.println("Flight: " + flight.getFlightName());
		System.out.println("Seat Class: " + seatClass);
		System.out.println("Tickets: " + ticketCount);
		System.out.println("Total Fare: " + totalFare);
	}

	public void viewBookings(Passenger passenger) {

		List<Booking> bookings = bookingDb.get(passenger);

		if (bookings == null || bookings.isEmpty()) {
			System.out.println("No bookings found");
			return;
		}

		System.out.println(
				"======================================================================================================================================================================");
		System.out.printf("%-10s %-12s %-12s %-20s %-20s %-12s %-8s %-12s %-20s %-20s %-10s%n", "BookingID", "From",
				"To", "Departure", "Arrival", "SeatClass", "Tickets", "TotalFare", "Booked Time", "Cancelled Time",
				"Status");
		System.out.println(
				"======================================================================================================================================================================");

		for (Booking booking : bookings) {

			String bookingTime = booking.getBookingTime().format(DATE_TIME_FORMAT);
			String cancelTime = booking.getCancellationTime() == null ? "-"
					: booking.getCancellationTime().format(DATE_TIME_FORMAT);

			System.out.printf("%-10d %-12s %-12s %-20s %-20s %-12s %-8d %-12.2f %-20s %-20s %-10s%n",
					booking.getBookingId(), booking.getTrip().getFrom(), booking.getTrip().getTo(),
					booking.getTrip().getDepartureTime().format(DATE_TIME_FORMAT),
					booking.getTrip().getArrivalTime().format(DATE_TIME_FORMAT), booking.getSeatClass(),
					booking.getTicketCount(), booking.getTotalFare(), bookingTime, cancelTime,
					booking.isCancelled() ? "CANCELLED" : "COMPLETED");
		}

		System.out.println(
				"======================================================================================================================================================================");
	}

	public void cancelBookingById(Passenger passenger, int bookingId) {

	    List<Booking> bookings = bookingDb.get(passenger);

	    if (bookings == null || bookings.isEmpty()) {
	        throw new IllegalStateException("No bookings found");
	    }

	    Booking booking = findBookingById(bookings, bookingId);

	    if (booking == null) {
	        throw new IllegalArgumentException("Booking ID not found");
	    }

	    if (booking.isCancelled()) {
	        throw new IllegalStateException("Booking already cancelled");
	    }

	    Trip trip = booking.getTrip();  

	    if (trip.getStatus() != TripStatus.YET_TO_DEPART) {
	        throw new IllegalStateException(
	            "Booking can be cancelled only before trip departure"
	        );
	    }

	    Flight flight = booking.getFlight();

	    for (int i = 0; i < booking.getTicketCount(); i++) {
	        if (booking.getSeatClass() == SeatClass.ECONOMY) {
	            flight.cancelEconomySeat();
	        } else {
	            flight.cancelBusinessSeat();
	        }
	    }

	    passenger.setWallet(
	        passenger.getWallet() + booking.getTotalFare()
	    );

	    booking.cancel();

	    System.out.println("Booking cancelled successfully");
	    System.out.println("Booking ID: " + bookingId);
	    System.out.println("Cancelled at: " + booking.getCancellationTime());
	}


	private Booking findBookingById(List<Booking> bookings, int bookingId) {
		for (Booking booking : bookings) {
			if (booking.getBookingId() == bookingId) {
				return booking;
			}
		}
		return null;
	}
}
