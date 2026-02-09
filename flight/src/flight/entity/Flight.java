package flight.entity;

import java.util.List;
import java.util.ArrayList;

public class Flight {

	private String flightNo;
	private String flightName;
	private int totalEconomySeats;
	private int availableEconomySeats;
	private int totalBusinessSeats;
	private int availableBusinessSeats;

	private List<Trip> route = new ArrayList<>();

	public Flight(String flightNo, String flightName, int totalEconomySeats, int totalBusinessSeats, List<Trip> route) {

		this.flightNo = flightNo;
		this.flightName = flightName;
		this.totalEconomySeats = totalEconomySeats;
		this.availableEconomySeats = totalEconomySeats;
		this.totalBusinessSeats = totalBusinessSeats;
		this.availableBusinessSeats = totalBusinessSeats;
		this.route = route;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getFlightName() {
		return flightName;
	}

	public void setFlightName(String flightName) {
		this.flightName = flightName;
	}

	public int getTotalEconomySeats() {
		return totalEconomySeats;
	}

	public int getAvailableEconomySeats() {
		return availableEconomySeats;
	}

	public int getTotalBusinessSeats() {
		return totalBusinessSeats;
	}

	public int getAvailableBusinessSeats() {
		return availableBusinessSeats;
	}

	public List<Trip> getRoute() {
		return route;
	}

	public void setRoute(List<Trip> route) {
		this.route = route;
	}

	public boolean reserveEconomySeat() {
		if (availableEconomySeats > 0) {
			availableEconomySeats--;
			return true;
		}
		return false;
	}

	public boolean reserveBusinessSeat() {
		if (availableBusinessSeats > 0) {
			availableBusinessSeats--;
			return true;
		}
		return false;
	}

	public void cancelEconomySeat() {
		if (availableEconomySeats < totalEconomySeats) {
			availableEconomySeats++;
		}
	}

	public void cancelBusinessSeat() {
		if (availableBusinessSeats < totalBusinessSeats) {
			availableBusinessSeats++;
		}
	}

	public void setTotalEconomySeats(int totalEconomySeats) {
		this.totalEconomySeats = totalEconomySeats;
	}

	public void setAvailableEconomySeats(int availableEconomySeats) {
		this.availableEconomySeats = availableEconomySeats;
	}

	public void setTotalBusinessSeats(int totalBusinessSeats) {
		this.totalBusinessSeats = totalBusinessSeats;
	}

	public void setAvailableBusinessSeats(int availableBusinessSeats) {
		this.availableBusinessSeats = availableBusinessSeats;
	}

	@Override
	public String toString() {
		return "Flight [flightNo=" + flightNo + ", flightName=" + flightName + ", economy=" + availableEconomySeats
				+ "/" + totalEconomySeats + ", business=" + availableBusinessSeats + "/" + totalBusinessSeats
				+ ", trip=" + route + "]";
	}
}
