package movieticket.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Theatre {

    private int tId;
    private String theatreName;
    private String location;
    private LocalDateTime showTime;
    private List<Movies> movies = new ArrayList<>();

    public Theatre(int tId, String theatreName, String location, LocalDateTime showTime) {
        this.tId = tId;
        this.theatreName = theatreName;
        this.location = location;
        this.showTime = showTime;
    }

    public List<Movies> getMovies() {
        return movies;
    }

    public void addMovie(Movies movie) {
        movies.add(movie);
    }

    @Override
    public String toString() {
        return "Theatre [tId=" + tId + ", name=" + theatreName +
                ", location=" + location + ", showTime=" + showTime +
                ", movies=" + movies + "]";
    }
}
