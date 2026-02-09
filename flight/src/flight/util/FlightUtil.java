package flight.util;

import flight.entity.Flight;
import flight.entity.Trip;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.*;

public class FlightUtil {

	private static final Scanner sc = new Scanner(System.in);

	private static final DateTimeFormatter DATE_TIME_FORMAT = new DateTimeFormatterBuilder().parseCaseInsensitive()
			.appendPattern("yyyy-MM-dd hh:mm a").toFormatter(Locale.ENGLISH);

	private static void validateNotEmpty(String value, String field) {
		if (value == null || value.trim().isEmpty()) {
			throw new IllegalArgumentException(field + " cannot be empty");
		}
	}
	private static void validatePositive(int value, String fieldName) {
	    if (value <= 0) {
	        throw new IllegalArgumentException(fieldName + " must be a positive number");
	    }
	}

	private static void validateTime(LocalDateTime dep, LocalDateTime arr) {
		LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);

		if (!dep.isAfter(now)) {
			throw new IllegalArgumentException("Departure time must be in the future");
		}

		if (!arr.isAfter(dep)) {
			throw new IllegalArgumentException("Arrival time must be after departure time");
		}
	}

	// ---------- INPUTS ----------

	public static String inputFlightNo() {
		System.out.print("Flight No: ");
		String flightNo = sc.nextLine();
		validateNotEmpty(flightNo, "Flight No");
		return flightNo;
	}
	public static int inputTripId() {
	    System.out.print("Enter Trip ID: ");
	    int tripId = sc.nextInt();
	    sc.nextLine();
	    validatePositive(tripId, "Trip ID");
	    return tripId;
	}


	public static String inputFlightName() {
		System.out.print("Flight Name: ");
		String name = sc.nextLine();
		validateNotEmpty(name, "Flight Name");
		return name;
	}

	public static int inputRouteIndex() {
		System.out.print("Route Index: ");
		int idx = sc.nextInt();
		sc.nextLine();
		return idx;
	}

	private static LocalDateTime readDateTime(String label) {
	    while (true) {
	        try {
	            System.out.print(label);
	            String input = sc.nextLine().trim();

	            LocalDateTime dateTime = LocalDateTime.parse(input, DATE_TIME_FORMAT);

	            return dateTime.withSecond(0).withNano(0);

	        } catch (DateTimeParseException e) {
	            System.out.println("Invalid format. Example: 2026-02-10 02:30 PM");
	        }
	    }
	}


	// ---------- ROUTE ----------
	public static Trip inputRoute() {

		System.out.print("From: ");
		String from = sc.nextLine();
		validateNotEmpty(from, "From");

		System.out.print("To: ");
		String to = sc.nextLine();
		validateNotEmpty(to, "To");

		LocalDateTime dep = readDateTime("Departure (yyyy-MM-dd hh:mm AM/PM): ");
		LocalDateTime arr = readDateTime("Arrival   (yyyy-MM-dd hh:mm AM/PM): ");

		validateTime(dep, arr);

		System.out.print("Economy Fare: ");
		double ecoFare = sc.nextDouble();

		System.out.print("Business Fare: ");
		double busFare = sc.nextDouble();
		sc.nextLine();

		return new Trip(from, to, dep, arr, ecoFare, busFare);
	}

	// ---------- FLIGHT ----------

	public static Flight inputFlight() {

		String flightNo = inputFlightNo();
		String flightName = inputFlightName();

		System.out.print("Total Economy Seats: ");
		int totalEconomySeats = sc.nextInt();

		System.out.print("Total Business Seats: ");
		int totalBusinessSeats = sc.nextInt();
		sc.nextLine();

		List<Trip> routes = new ArrayList<>();
		routes.add(inputRoute());

		return new Flight(flightNo, flightName, totalEconomySeats, totalBusinessSeats, routes);
	}

}
