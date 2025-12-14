package bank.main;

import java.util.Scanner;
import BankService.BankServices;
import BankService.BankServicesImpl;

public class Bank {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        BankServices bankService = new BankServicesImpl(); // INTERFACE reference

        while (true) {
            System.out.println("\n--- BANK MENU ---");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Check Balance");
            System.out.println("5. Exit");

            System.out.print("Choose option: ");
            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Account Number: ");
                    int accNo = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Holder Name: ");
                    String name = sc.nextLine();
                    System.out.print("Initial Balance: ");
                    double balance = sc.nextDouble();
                    bankService.addAccount(accNo, name, balance);
                    break;

                case 2:
                    System.out.print("Account Number & Amount: ");
                    bankService.deposit(sc.nextInt(), sc.nextDouble());
                    break;

                case 3:
                    System.out.print("Account Number & Amount: ");
                    bankService.withDraw(sc.nextInt(), sc.nextDouble());
                    break;

                case 4:
                    System.out.print("Account Number: ");
                    bankService.checkBalance(sc.nextInt());
                    break;

                case 5:
                    System.out.println("👋 Thank you!");
                    System.exit(0);

                default:
                    System.out.println("❌ Invalid option");
            }
        }
    }
}
