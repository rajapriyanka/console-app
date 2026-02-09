package flight.entity;

import java.time.LocalDateTime;

import flight.util.SeatClass;

public class Booking {

	private static int bookingCounter = 100000;

	private final int bookingId;
	private final Flight flight;
	private final Trip route;
	private final SeatClass seatClass;
	private final int ticketCount;
	private final double totalFare;
	private final LocalDateTime bookingTime;
	private LocalDateTime cancellationTime;

	public void setCancellationTime(LocalDateTime cancellationTime) {
		this.cancellationTime = cancellationTime;
	}

	public Booking(Flight flight, Trip route, SeatClass seatClass, int ticketCount, double totalFare) {

		if (ticketCount <= 0) {
			throw new IllegalArgumentException("Ticket count must be greater than 0");
		}

		this.bookingId = ++bookingCounter;
		this.flight = flight;
		this.route = route;
		this.seatClass = seatClass;
		this.ticketCount = ticketCount;
		this.totalFare = totalFare;
		this.bookingTime = LocalDateTime.now();
	}

	public int getBookingId() {
		return bookingId;
	}

	public Flight getFlight() {
		return flight;
	}

	public Trip getTrip() {
		return route;
	}

	public SeatClass getSeatClass() {
		return seatClass;
	}

	public int getTicketCount() {
		return ticketCount;
	}

	public double getTotalFare() {
		return totalFare;
	}

	public LocalDateTime getBookingTime() {
		return bookingTime;
	}

	public LocalDateTime getCancellationTime() {
		return cancellationTime;
	}

	public void cancel() {
		if (isCancelled()) {
			throw new IllegalStateException("Booking already cancelled");
		}
		this.cancellationTime = LocalDateTime.now();
	}

	public boolean isCancelled() {
		return cancellationTime != null;
	}

	@Override
	public String toString() {
		return "Booking [bookingId=" + bookingId + ", flight=" + flight.getFlightNo() + ", route=" + route
				+ ", seatClass=" + seatClass + ", ticketCount=" + ticketCount + ", totalFare=" + totalFare
				+ ", bookingTime=" + bookingTime + ", cancellationTime=" + cancellationTime + "]";
	}
}
