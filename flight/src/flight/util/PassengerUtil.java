package flight.util;

import java.util.Scanner;
import java.util.regex.Pattern;

import flight.entity.Passenger;

public class PassengerUtil {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    private static final Pattern PHONE_PATTERN = Pattern.compile("^[0-9]{10}$");
    
    private static final Scanner sc = new Scanner(System.in);
    
    public static String inputUsername() {
        System.out.print("UserName: ");
        String userName = sc.nextLine();
        validateNotEmpty(userName, "User Name");
        return userName;
    }
    
    public static double inputWallet() {
        System.out.print("Enter the amount: ");
        double wallet = sc.nextDouble();
        validateWallet(wallet);
        return wallet;
    }
    
    public static Passenger passengerInput() {

  	
        System.out.print("Username: ");
        String username = sc.nextLine();
        validateNotEmpty(username, "Username");
        
        System.out.print("Password: ");
        String password = sc.nextLine();
        validateNotEmpty(password, "Password");
        validatePassword(password);

        System.out.print("First Name: ");
        String firstName = sc.nextLine();
        validateNotEmpty(firstName, "First Name");

        System.out.print("Last Name: ");
        String lastName = sc.nextLine();
        validateNotEmpty(lastName, "Last Name");

        System.out.print("Email: ");
        String email = sc.nextLine();
        validateNotEmpty(email, "Email");
        validateEmail(email);

        System.out.print("Phone: ");
        String phone = sc.nextLine();
        validateNotEmpty(phone, "Phone");
        validatePhone(phone);

        System.out.print("Wallet Amount: ");
        double wallet = sc.nextDouble();
        validateWallet(wallet);

        return new Passenger(username, password, firstName, lastName, email, phone, wallet);
    }
    
    

    public static void validateNotEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty");
        }
    }

    public static void validateUserName(String userName) {
        if (userName == null || userName.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
    }

    public static void validatePassword(String password) {
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters");
        }
    }

    public static void validateEmail(String email) {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    public static void validatePhone(String phone) {
        if (phone == null || !PHONE_PATTERN.matcher(phone).matches()) {
            throw new IllegalArgumentException("Phone must be 10 digits");
        }
    }

    public static void validateWallet(double wallet) {
        if (wallet < 0) {
            throw new IllegalArgumentException("Wallet balance cannot be negative");
        }
    }
}
