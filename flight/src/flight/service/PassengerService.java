package flight.service;

import flight.util.PassengerUtil;
import flight.entity.Passenger;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PassengerService {

    private final Map<String, Passenger> passengerDB = new HashMap<>();

    // ---------------- CREATE ----------------
    
    public void addPassenger(Passenger passenger) {
    	PassengerUtil.validateUserName(passenger.getUserName());
    	PassengerUtil.validatePassword(passenger.getPassword());
        PassengerUtil.validateEmail(passenger.getEmail());
        PassengerUtil.validatePhone(passenger.getPhone());
        PassengerUtil.validateWallet(passenger.getWallet());

        if (passengerDB.containsKey(passenger.getUserName())) {
            throw new IllegalArgumentException("Passenger already exists");
        }

        passengerDB.put(passenger.getUserName(), passenger);
    }

    // ---------------- READ by username ----------------
    public Passenger getPassenger(String userName) {
    	PassengerUtil.validateUserName(userName);

        Passenger passenger = passengerDB.get(userName);
        if (passenger == null) {
            throw new IllegalArgumentException("Passenger not found");
        }
        return passenger;
    }

    // ---------------- READ ALL ----------------
     public Collection<Passenger> getAllPassengers() {
        return passengerDB.values();
    }

    // ---------------- UPDATE ----------------
     public void updatePassenger(Passenger updated) {

    	    Passenger existing = passengerDB.get(updated.getUserName());

    	    if (existing == null) {
    	        throw new IllegalArgumentException("Passenger not found");
    	    }

    	    existing.setFirstName(updated.getFirstName());
    	    existing.setLastName(updated.getLastName());
    	    existing.setEmail(updated.getEmail());
    	    existing.setPhone(updated.getPhone());
    	    existing.setWallet(updated.getWallet());
    	}

    // ---------------- UPDATE WALLET ----------------
    
    public void updateWallet(String userName, double amount) {
        Passenger passenger = getPassenger(userName);
        PassengerUtil.validateWallet(passenger.getWallet() + amount);

        passenger.setWallet(passenger.getWallet() + amount);
    }

    // ---------------- DELETE ----------------
    public void deletePassenger(String userName) {
    	PassengerUtil.validateUserName(userName);

        if (!passengerDB.containsKey(userName)) {
            throw new IllegalArgumentException("Passenger not found");
        }
        passengerDB.remove(userName);
        passengerDB.values();
    }
}
