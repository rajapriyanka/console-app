package flight.service;

import flight.entity.Booking;
import flight.entity.Flight;
import flight.entity.Passenger;
import flight.entity.Trip;
import flight.util.SeatClass;
import flight.util.TripStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FlightService {

	public final Map<String, Flight> flightDB = new HashMap<>();

	private final BookingService bookingService;

	private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

	public FlightService(BookingService bookingService) {
		this.bookingService = bookingService;
	}
	// ---------------- CREATE ----------------

	public void addFlight(Flight flight) {

		if (flight.getFlightNo() == null || flight.getFlightNo().trim().isEmpty()) {
			throw new IllegalArgumentException("Flight number cannot be empty");
		}

		if (flight.getFlightName() == null || flight.getFlightName().trim().isEmpty()) {
			throw new IllegalArgumentException("Flight name cannot be empty");
		}

		if (flightDB.containsKey(flight.getFlightNo())) {
			throw new IllegalArgumentException("Flight number already exists");
		}

		if (flight.getRoute() == null || flight.getRoute().isEmpty()) {
			throw new IllegalArgumentException("At least one route is required");
		}

		for (Trip route : flight.getRoute()) {
			validateRoute(route);
		}

		flightDB.put(flight.getFlightNo(), flight);
	}

	public Map<String, Flight> getFlightDb() {
		return flightDB;
	}

	// ---------------- READ ----------------
	public Flight getFlight(String flightNo) {
		if (flightNo == null || flightNo.trim().isEmpty()) {
			throw new IllegalArgumentException("Flight number cannot be empty");
		}

		Flight flight = flightDB.get(flightNo);
		if (flight == null) {
			throw new IllegalArgumentException("Flight not found");
		}
		return flight;
	}

	// ---------------- READ ALL ----------------
	public Collection<Flight> getAllFlights() {
		return flightDB.values();
	}

	// ---------------- UPDATE ----------------
	public void updateFlightName(String flightNo, String newName) {
		Flight flight = getFlight(flightNo);

		if (newName == null || newName.trim().isEmpty()) {
			throw new IllegalArgumentException("Flight name cannot be empty");
		}

		flight.setFlightName(newName);
	}

	// ---------------- ADD ROUTE ----------------
	public void addRoute(String flightNo, Trip route) {
		Flight flight = getFlight(flightNo);

		validateRoute(route);

		List<Trip> routes = flight.getRoute();

		if (!routes.isEmpty()) {
			Trip lastRoute = routes.get(routes.size() - 1);

			if (!route.getDepartureTime().isAfter(lastRoute.getArrivalTime())) {
				throw new IllegalArgumentException("New Trip departure time must be after previous trip arrival time");
			}
		}

		routes.add(route);
	}
	private int getTripIndexById(List<Trip> trips, int tripId) {
		for (int i = 0; i < trips.size(); i++) {
			if (trips.get(i).getTripId() == tripId) {
				return i;
			}
		}
		return -1;
	}

	// ---------------- UPDATE ROUTE ----------------
	public void updateRoute(String flightNo, int tripId, Trip updatedRoute) {

		Flight flight = getFlight(flightNo);
		List<Trip> routes = flight.getRoute();

		int index = getTripIndexById(routes, tripId);

		if (index == -1) {
			throw new IllegalArgumentException("Invalid Trip ID");
		}

		Trip existingTrip = routes.get(index);

		if (existingTrip.getStatus() != TripStatus.YET_TO_DEPART) {
			throw new IllegalStateException(
				"Trip cannot be updated once it has started, completed, or cancelled"
			);
		}

		validateRoute(updatedRoute);

		if (index > 0) {
			Trip previous = routes.get(index - 1);
			if (!updatedRoute.getDepartureTime().isAfter(previous.getArrivalTime())) {
				throw new IllegalArgumentException(
					"Trip departure time must be after previous trip arrival time"
				);
			}
		}

		if (index < routes.size() - 1) {
			Trip next = routes.get(index + 1);
			if (!updatedRoute.getArrivalTime().isBefore(next.getDepartureTime())) {
				throw new IllegalArgumentException(
					"Trip arrival time must be before next trip departure time"
				);
			}
		}

		updatedRoute.setStatus(existingTrip.getStatus());
		updatedRoute.setActualDepartureTime(existingTrip.getActualDepartureTime());
		updatedRoute.setActualArrivalTime(existingTrip.getActualArrivalTime());

		routes.set(index, updatedRoute);
	}

	public void startTrip(String flightNo, int tripId) {

		Flight flight = getFlight(flightNo);
		Trip trip = getTripById(flight.getRoute(), tripId);

		if (trip == null) {
			throw new IllegalArgumentException("Invalid Trip ID");
		}

		if (trip.getStatus() != TripStatus.YET_TO_DEPART) {
			throw new IllegalStateException("Trip cannot be started");
		}

		LocalDateTime now = LocalDateTime.now();

		trip.setActualDepartureTime(now);
		trip.setStatus(TripStatus.TRAVELLING);

		System.out.println("Trip started at " + now + " (Scheduled: " + trip.getDepartureTime() + ")");
	}

	public void endTrip(String flightNo, int tripId) {

	    Flight flight = getFlight(flightNo);
	    Trip trip = getTripById(flight.getRoute(), tripId);

	    if (trip == null) {
	        throw new IllegalArgumentException("Invalid Trip ID");
	    }

	    if (trip.getStatus() != TripStatus.TRAVELLING) {
	        throw new IllegalStateException("Trip is not ongoing");
	    }
	    LocalDateTime now = LocalDateTime.now();

	    trip.setActualArrivalTime(now);
	    trip.setStatus(TripStatus.COMPLETED);
	    bookingService.restoreSeatsByTrip(trip, flight);

	    System.out.println("Trip ended at " + now +
	        " (Scheduled: " + trip.getArrivalTime() + ")");
	}


	public void cancelRoute(String flightNo, int tripId) {

		Flight flight = getFlight(flightNo);
		Trip trip = getTripById(flight.getRoute(), tripId);

		if (trip == null) {
			throw new IllegalArgumentException("Invalid Trip ID");
		}

		if (trip.getStatus() == TripStatus.CANCELLED) {
			throw new IllegalStateException("Trip already cancelled");
		}
		if (trip.getStatus() == TripStatus.TRAVELLING) {

			throw new IllegalStateException("Trip is already ongoing");
		}
		if (trip.getStatus() == TripStatus.COMPLETED) {

			throw new IllegalStateException("Trip is already completed");
		}

		bookingService.cancelBookingsByTrip(trip);

		trip.setStatus(TripStatus.CANCELLED);

		System.out.println("Trip " + tripId + " cancelled successfully");
		System.out.println("All related bookings refunded and cancelled");
	}

	private Trip getTripById(List<Trip> trips, int tripId) {
		for (Trip trip : trips) {
			if (trip.getTripId() == tripId) {
				return trip;
			}
		}
		return null;
	}

	public void viewAllTrips(String flightNo) {

		Flight flight = getFlight(flightNo);
		List<Trip> trips = flight.getRoute();

		if (trips == null || trips.isEmpty()) {
			System.out.println("No trips available for this flight.");
			return;
		}

		System.out.println("\nTrips for Flight " + flightNo + " (" + flight.getFlightName() + ")");

		System.out.println(
				"======================================================================================================================================================================");
		System.out.printf("%-8s %-12s %-12s %-20s %-20s %-20s %-20s %-12s %-10s %-10s%n", "TRIP ID", "FROM", "TO",
				"SCH DEP", "SCH ARR", "ACT DEP", "ACT ARR", "STATUS", "ECO", "BUS");
		System.out.println(
				"======================================================================================================================================================================");

		for (Trip trip : trips) {

			String actualDep = trip.getActualDepartureTime() == null ? "-"
					: trip.getActualDepartureTime().format(DATE_TIME_FORMAT);

			String actualArr = trip.getActualArrivalTime() == null ? "-"
					: trip.getActualArrivalTime().format(DATE_TIME_FORMAT);

			System.out.printf("%-8d %-12s %-12s %-20s %-20s %-20s %-20s %-12s %-10.2f %-10.2f%n", trip.getTripId(),
					trip.getFrom(), trip.getTo(), trip.getDepartureTime().format(DATE_TIME_FORMAT),
					trip.getArrivalTime().format(DATE_TIME_FORMAT), actualDep, actualArr, trip.getStatus(),
					trip.getEconomyFare(), trip.getBusinessFare());
		}

		System.out.println(
				"======================================================================================================================================================================");
	}

	// ---------------- DELETE FLIGHT ----------------

	public void deleteFlight(String flightNo) {
		if (!flightDB.containsKey(flightNo)) {
			throw new IllegalArgumentException("Flight not found");
		}
		flightDB.remove(flightNo);
		flightDB.values();
	}

	// ----------------- VALIDATIONS ------------------

	private void validateRoute(Trip route) {

		if (route.getFrom() == null || route.getFrom().trim().isEmpty()) {
			throw new IllegalArgumentException("Route source cannot be empty");
		}

		if (route.getTo() == null || route.getTo().trim().isEmpty()) {
			throw new IllegalArgumentException("Route destination cannot be empty");
		}

		if (route.getFrom().equalsIgnoreCase(route.getTo())) {
			throw new IllegalArgumentException("Source and destination cannot be same");
		}

		LocalDateTime dep = route.getDepartureTime();
		LocalDateTime arr = route.getArrivalTime();

		if (dep == null || arr == null) {
			throw new IllegalArgumentException("Departure and arrival time required");
		}

		if (!arr.isAfter(dep)) {
			throw new IllegalArgumentException("Arrival time must be after departure time");
		}

		if (route.getEconomyFare() <= 0 || route.getBusinessFare() <= 0) {
			throw new IllegalArgumentException("Fare must be greater than zero");
		}
	}
}
