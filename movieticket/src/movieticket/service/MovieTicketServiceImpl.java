package movieticket.service;

import java.time.LocalDateTime;
import java.util.*;

import movieticket.model.*;

public class MovieTicketServiceImpl implements MovieTicketService {

    Scanner s = new Scanner(System.in);

    private List<Users> users = new ArrayList<>();

    
    

    private List<Theatre> theatres = new ArrayList<>();

    private Map<Integer, List<History>> history = new HashMap<>();

    public MovieTicketServiceImpl() {
        theatres.add(new Theatre(1, "PVR", "Coimbatore", LocalDateTime.now()));
    }

    public void addUser() {
        System.out.println("Enter User Id");
        int id = s.nextInt();

        for (Users u : users) {
            if (u.getId() == id) {
                System.out.println("User already exists");
                return;
            }
        }

        System.out.println("Enter First Name");
        String fname = s.next();
        System.out.println("Enter Last Name");
        String lname = s.next();
        System.out.println("Enter Age");
        int age = s.nextInt();
        System.out.println("Enter Initial Wallet");
        double wallet = s.nextDouble();

        users.add(new Users(id, fname, lname, age, wallet));
        System.out.println("User added successfully");
    }

    public void updateUser() {
        System.out.println("Enter User Id");
        int id = s.nextInt();

        for (Users u : users) {
            if (u.getId() == id) {
                System.out.println("Enter New First Name");
                u.setFname(s.next());
                System.out.println("Enter Wallet");
                u.setWallet(s.nextDouble());
                System.out.println("User updated");
                return;
            }
        }
        System.out.println("User not found");
    }

    public void deleteUser() {
        System.out.println("Enter User Id");
        int id = s.nextInt();

        for (Users u : users) {
            if (u.getId() == id) {
                users.remove(u);
                history.remove(id);
                System.out.println("User deleted");
                return;
            }
        }
        System.out.println("User not found");
    }

    public void getUser() {
        System.out.println("Enter User Id");
        int id = s.nextInt();

        for (Users u : users) {
            if (u.getId() == id) {
                System.out.println(u);
                return;
            }
        }
        System.out.println("User not found");
    }

    public void viewAllUsers() {
        if (users.size() == 0) {
            System.out.println("No users available");
            return;
        }

        for (Users u : users) {
            System.out.println(u);
        }
    }

    public void addMovie() {
        Theatre t = theatres.get(0);

        System.out.println("Enter Movie Id");
        int id = s.nextInt();

        for (Movies m : t.getMovies()) {
            if (m.getmId() == id) {
                System.out.println("Movie already exists");
                return;
            }
        }

        System.out.println("Enter Movie Name");
        String name = s.next();
        System.out.println("Enter Ticket Rate");
        double rate = s.nextDouble();
        System.out.println("Enter Genre");
        String genre = s.next();

        t.addMovie(new Movies(id, name, rate, genre));
        System.out.println("Movie added under theatre");
    }

    public void updateMovie() {
        System.out.println("Enter Movie Id");
        int id = s.nextInt();

        for (Movies m : theatres.get(0).getMovies()) {
            if (m.getmId() == id) {
                System.out.println("Enter Movie Name");
                m.setmName(s.next());
                System.out.println("Enter Ticket Rate");
                m.setTicketRate(s.nextDouble());
                System.out.println("Movie updated");
                return;
            }
        }
        System.out.println("Movie not found");
    }

    public void deleteMovie() {
        System.out.println("Enter Movie Id");
        int id = s.nextInt();

        List<Movies> movies = theatres.get(0).getMovies();

        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getmId() == id) {
                movies.remove(i);
                System.out.println("Movie deleted");
                return;
            }
        }
        System.out.println("Movie not found");
    }

    public void getMovie() {
        System.out.println("Enter Movie Id");
        int id = s.nextInt();

        for (Movies m : theatres.get(0).getMovies()) {
            if (m.getmId() == id) {
                System.out.println(m);
                return;
            }
        }
        System.out.println("Movie not found");
    }

    public void viewAllMovies() {
        for (Movies m : theatres.get(0).getMovies()) {
            System.out.println(m);
        }
    }

    public void booking() {

        System.out.println("Enter User Id");
        int userId = s.nextInt();

        System.out.println("Enter Movie Id");
        int movieId = s.nextInt();

        Users user = null;
        Movies movie = null;

        for (Users u : users) {
            if (u.getId() == userId) {
                user = u;
                break;
            }
        }

        for (Movies m : theatres.get(0).getMovies()) {
            if (m.getmId() == movieId) {
                movie = m;
                break;
            }
        }

        if (user == null || movie == null) {
            System.out.println("User or Movie not found");
            return;
        }

        if (user.getWallet() < movie.getTicketRate()) {
            System.out.println("Not enough balance");
            return;
        }

        user.setWallet(user.getWallet() - movie.getTicketRate());

        History h = new History(
                userId,
                LocalDateTime.now(),
                null,
                user.getFname(),
                movie.getmName()
        );

        if (!history.containsKey(userId)) {
            history.put(userId, new ArrayList<>());
        }
        history.get(userId).add(h);

        System.out.println("Ticket booked successfully");
    }


    public void cancel() {

        System.out.println("Enter User Id");
        int userId = s.nextInt();

        if (!history.containsKey(userId)) {
            System.out.println("No booking found");
            return;
        }

        List<History> list = history.get(userId);

        if (list.size() == 0) {
            System.out.println("No booking found");
            return;
        }

        History h = list.get(list.size() - 1);

        String movieName = h.getmName();
        double refund = 0;

        for (Movies m : theatres.get(0).getMovies()) {
            if (m.getmName().equals(movieName)) {
                refund = m.getTicketRate();
                break;
            }
        }

        for (Users u : users) {
            if (u.getId() == userId) {
                u.setWallet(u.getWallet() + refund);
                h.setCancelTime(LocalDateTime.now());
                System.out.println("Ticket cancelled successfully");
                System.out.println("Refund Amount: ₹" + refund);
                return;
            }
        }
    }

    public void viewHistory() {
        for (Integer uid : history.keySet()) {
            System.out.println("User Id: " + uid);
            for (History h : history.get(uid)) {
                System.out.println(h);
            }
        }
    }
}
