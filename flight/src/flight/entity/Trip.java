package flight.entity;

import java.time.LocalDateTime;
import flight.util.TripStatus;

public class Trip {

    private static int tripCounter = 100;

    private final int tripId;  
    private String from;
    private String to;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private LocalDateTime actualDepartureTime;
    private LocalDateTime actualArrivalTime;
    private double economyFare;
    private double businessFare;
    private TripStatus status;
    public LocalDateTime getActualDepartureTime() {
		return actualDepartureTime;
	}

	public void setActualDepartureTime(LocalDateTime actualDepartureTime) {
		this.actualDepartureTime = actualDepartureTime;
	}

	public LocalDateTime getActualArrivalTime() {
		return actualArrivalTime;
	}

	public void setActualArrivalTime(LocalDateTime actualArrivalTime) {
		this.actualArrivalTime = actualArrivalTime;
	}
    public Trip(String from, String to, LocalDateTime departureTime,
                LocalDateTime arrivalTime, double economyFare, double businessFare) {

        this.tripId = ++tripCounter;  
        this.from = from;
        this.to = to;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.economyFare = economyFare;
        this.businessFare = businessFare;
        this.status = TripStatus.YET_TO_DEPART;
    }

    public int getTripId() {   
        return tripId;
    }

	public TripStatus getStatus() {
		return status;
	}



	@Override
	public String toString() {
		return "Trip [tripId=" + tripId + ", from=" + from + ", to=" + to + ", departureTime=" + departureTime
				+ ", arrivalTime=" + arrivalTime + ", actualDepartureTime=" + actualDepartureTime
				+ ", actualArrivalTime=" + actualArrivalTime + ", economyFare=" + economyFare + ", businessFare="
				+ businessFare + ", status=" + status + "]";
	}

	public static int getTripCounter() {
		return tripCounter;
	}

	public static void setTripCounter(int tripCounter) {
		Trip.tripCounter = tripCounter;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public LocalDateTime getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(LocalDateTime departureTime) {
		this.departureTime = departureTime;
	}

	public LocalDateTime getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(LocalDateTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public double getEconomyFare() {
		return economyFare;
	}

	public void setEconomyFare(double economyFare) {
		this.economyFare = economyFare;
	}

	public double getBusinessFare() {
		return businessFare;
	}

	public void setBusinessFare(double businessFare) {
		this.businessFare = businessFare;
	}

	public void setStatus(TripStatus status) {
		this.status = status;
	}
}
