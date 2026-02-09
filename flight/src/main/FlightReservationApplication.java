package main;

import flight.entity.Flight;
import flight.entity.Passenger;
import flight.entity.Trip;
import flight.service.PassengerService;
import flight.util.FlightUtil;
import flight.util.PassengerUtil;
import flight.util.SeatClass;
import flight.service.BookingService;
import flight.service.FlightService;
import java.util.*;

public class FlightReservationApplication {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		PassengerService passengerService = new PassengerService();
		BookingService bookingService = new BookingService();
		FlightService flightService = new FlightService(bookingService);

		while (true) {
			System.out.println("\n======== FLIGHT RESERVATION SYSTEM =====");
			System.out.println("--------Passenger---------");
			System.out.println("1. Add Passenger");
			System.out.println("2. View Passenger");
			System.out.println("3. View All Passengers");
			System.out.println("4. Update Passenger");
			System.out.println("5. Add Money in Wallet");
			System.out.println("6. Delete Passenger");

			System.out.println("--------Flight---------");
			System.out.println("7. Add Flight");
			System.out.println("8. View Flight");
			System.out.println("9. View All Flights");
			System.out.println("10. Update Flight Name");
			System.out.println("11. Add Trip");
			System.out.println("12. Update Trip");
			System.out.println("13. Cancel Trip");
			System.out.println("14. View all Trip");
			System.out.println("15. Delete Flight");

			System.out.println("--------Booking & Cancellation---------");
			System.out.println("16.Book a ticket");
			System.out.println("17.Cancel a ticket");
			System.out.println("18.View All Bookings");

			System.out.println("--------Trip Operations---------");
			System.out.println("19. Start Trip");
			System.out.println("20. End Trip");

			System.out.println("21. Exit");

			System.out.print("Enter choice: ");
			int choice = sc.nextInt();
			sc.nextLine();

			try {
				switch (choice) {

				case 1: {
					Passenger passenger = PassengerUtil.passengerInput();
					passengerService.addPassenger(passenger);
					System.out.println("Passenger added");
					break;
				}

				case 2: {
					Passenger passenger = passengerService.getPassenger(PassengerUtil.inputUsername());
					System.out.println(passenger);
					break;
				}

				case 3:
					passengerService.getAllPassengers().forEach(passenger -> System.out.println(passenger));
					break;

				case 4: {
					Passenger updatedPassenger = PassengerUtil.passengerInput();
					passengerService.updatePassenger(updatedPassenger);
					System.out.println("Passenger updated");
					break;
				}

				case 5: {

					passengerService.updateWallet(PassengerUtil.inputUsername(), PassengerUtil.inputWallet());
					System.out.println("Money updated");
					break;
				}

				case 6: {
					passengerService.deletePassenger(PassengerUtil.inputUsername());
					System.out.println(" Passenger deleted");
					break;
				}

				case 7: {
					flightService.addFlight(FlightUtil.inputFlight());
					System.out.println("Flight added");
					break;
				}

				case 8: {
					System.out.println(flightService.getFlight(FlightUtil.inputFlightNo()));
					break;
				}

				case 9: {
					flightService.getAllFlights().forEach(System.out::println);
					break;
				}

				case 10: {
					String flightNo = FlightUtil.inputFlightNo();
					System.out.print("New Name: ");
					flightService.updateFlightName(flightNo, sc.nextLine());
					System.out.println("Flight name updated");
					break;
				}

				case 11: {
					flightService.addRoute(FlightUtil.inputFlightNo(), FlightUtil.inputRoute());
					System.out.println("Route added");
					break;
				}

				case 12: {

					String flightNo = FlightUtil.inputFlightNo();
					flightService.viewAllTrips(flightNo);
					int tripId = FlightUtil.inputTripId();
					System.out.println("Enter updated trip details:");
					Trip updatedRoute = FlightUtil.inputRoute();
					flightService.updateRoute(flightNo, tripId, updatedRoute);
					System.out.println("Route updated successfully");
					break;
				}


				case 13: {

					String flightNo = FlightUtil.inputFlightNo();
					flightService.viewAllTrips(flightNo);
					System.out.print("Enter Trip ID to cancel: ");
					int tripId = sc.nextInt();
					flightService.cancelRoute(flightNo, tripId);
					break;
				}

				case 14: {
					flightService.viewAllTrips(FlightUtil.inputFlightNo());
					break;
				}

				case 15: {
					flightService.deleteFlight(FlightUtil.inputFlightNo());
					System.out.println("Flight deleted");
					break;
				}

				case 16: {
					Passenger passenger = passengerService.getPassenger(PassengerUtil.inputUsername());

					System.out.print("From: ");
					String from = sc.nextLine();

					System.out.print("To: ");
					String to = sc.nextLine();

					Map<Integer, Trip> options = bookingService.searchFlightsByRoute(flightService.getFlightDb(), from,
							to);

					System.out.print("Select option number: ");
					int choiceOption = sc.nextInt();

					Trip selectedRoute = options.get(choiceOption);
					if (selectedRoute == null) {
						throw new IllegalArgumentException("Invalid selection");
					}
					System.out.print("Choose Class ECONOMY/BUSINESS: ");
					String seatStr = sc.next().trim().toUpperCase();
					SeatClass seatInput;

					try {
						seatInput = SeatClass.valueOf(seatStr);
					} catch (IllegalArgumentException e) {
						throw new IllegalArgumentException("Invalid seat class. Choose ECONOMY or BUSINESS");
					}

					System.out.print("Number of Tickets: ");
					int ticketCount = sc.nextInt();
					sc.nextLine();

					Flight selectedFlight = null;
					for (Flight f : flightService.getFlightDb().values()) {
						if (f.getRoute().contains(selectedRoute)) {
							selectedFlight = f;
							break;
						}
					}

					if (selectedFlight == null) {
						throw new IllegalStateException("Flight not found for selected route");
					}

					bookingService.bookTicket(passenger, selectedFlight, selectedRoute, seatInput, ticketCount);

					break;
				}

				case 17: {
					Passenger passenger = passengerService.getPassenger(PassengerUtil.inputUsername());

					bookingService.viewBookings(passenger);

					System.out.print("Select booking number to cancel: ");
					int bookingId = sc.nextInt();

					bookingService.cancelBookingById(passenger, bookingId);

					break;
				}

				case 18: {
					Passenger passenger = passengerService.getPassenger(PassengerUtil.inputUsername());
					bookingService.viewBookings(passenger);
					break;
				}
				case 19: {
					String flightNo = FlightUtil.inputFlightNo();
					flightService.viewAllTrips(flightNo);
					int tripId = FlightUtil.inputTripId();
					flightService.startTrip(flightNo, tripId);
					break;
				}
				case 20: {
					String flightNo = FlightUtil.inputFlightNo();
					flightService.viewAllTrips(flightNo);

					int tripId = FlightUtil.inputTripId();
					flightService.endTrip(flightNo, tripId);
					break;
				}

				case 21: {
					System.out.println("Thank you");
					sc.close();
					System.exit(0);
					break;
				}

				default:
					System.out.println("Invalid choice");
				}

			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
	}
}
