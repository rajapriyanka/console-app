package movieTicketApp;


import java.util.Scanner;
import movieticket.service.MovieTicketService;
import movieticket.service.MovieTicketServiceImpl;

public class MovieTicket {

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);
        MovieTicketService service = new MovieTicketServiceImpl();

        while (true) {
            System.out.println("--------------------------------");
            System.out.println("1. Add User");
            System.out.println("2. Delete User");
            System.out.println("3. View User");
            System.out.println("4. Update User");
            System.out.println("5. View All Users");
            System.out.println("6. Add Movie");
            System.out.println("7. Delete Movie");
            System.out.println("8. View Movie");
            System.out.println("9. Update Movie");
            System.out.println("10. View All Movies");
            System.out.println("11. Book Movie Ticket");
            System.out.println("12. Cancel Movie Ticket");
            System.out.println("13. View History");
            System.out.println("14. Exit");
            System.out.println("Enter your Choice");

            int choice = s.nextInt();

            switch (choice) {

                case 1:
                    service.addUser();
                    break;

                case 2:
                    service.deleteUser();
                    break;

                case 3:
                    service.getUser();
                    break;

                case 4:
                    service.updateUser();
                    break;

                case 5:
                    service.viewAllUsers();
                    break;

                case 6:
                    service.addMovie();
                    break;

                case 7:
                    service.deleteMovie();
                    break;

                case 8:
                    service.getMovie();
                    break;

                case 9:
                    service.updateMovie();
                    break;

                case 10:
                    service.viewAllMovies();
                    break;

                case 11:
                    service.booking();
                    break;

                case 12:
                    service.cancel();
                    break;

                case 13:
                    service.viewHistory();
                    break;

                case 14:
                    System.out.println("Thank you");
                    System.exit(0);

                default:
                    System.out.println("Invalid Choice");
            }
        }
    }
}
