import java.util.List;

class Movie {
    private String movieId;
    private String title;
    private String description;
    private int duration; // in minutes
    private String genre;
    private String director;
    private String cast;

    public Movie(String movieId, String title, String description, int duration, String genre, String director, String cast) {
        this.movieId = movieId;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.genre = genre;
        this.director = director;
        this.cast = cast;
    }

    public String getMovieDetails() {
        return "Title: " + title + ", Description: " + description + ", Duration: " + duration +
                " mins, Genre: " + genre + ", Director: " + director + ", Cast: " + cast;
    }
}

class Show {
    private String showId;
    private Movie movie;
    private String showtime;
    private Screen screen;
    private List<Seat> availableSeats;

    public Show(String showId, Movie movie, String showtime, Screen screen, List<Seat> availableSeats) {
        this.showId = showId;
        this.movie = movie;
        this.showtime = showtime;
        this.screen = screen;
        this.availableSeats = availableSeats;
    }

    public String getShowDetails() {
        return "Show ID: " + showId + ", Movie: " + movie.getMovieDetails() + ", Showtime: " + showtime + ", Screen: " + screen.getScreenId();
    }

    public void updateAvailableSeats(Seat seat, boolean isAvailable) {
        seat.setAvailable(isAvailable);
        if (isAvailable) {
            availableSeats.add(seat);
        } else {
            availableSeats.remove(seat);
        }
    }
}

class Screen {
    private String screenId;
    private List<Seat> seatLayout;

    public Screen(String screenId, List<Seat> seatLayout) {
        this.screenId = screenId;
        this.seatLayout = seatLayout;
    }

    public String getScreenId() {
        return screenId;
    }

    public List<Seat> getSeatLayout() {
        return seatLayout;
    }
}

class Seat {
    private String seatNumber;
    private String seatType;
    private boolean isAvailable;

    public Seat(String seatNumber, String seatType) {
        this.seatNumber = seatNumber;
        this.seatType = seatType;
        this.isAvailable = true;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public String getSeatType() {
        return seatType;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void reserveSeat() {
        if (isAvailable) {
            isAvailable = false;
        } else {
            throw new IllegalStateException("Seat is already reserved");
        }
    }

    public void releaseSeat() {
        isAvailable = true;
    }
}

class Booking {
    private String bookingId;
    private User user;
    private Show show;
    private List<Seat> seats;
    private Payment payment;
    private String bookingStatus;

    public Booking(String bookingId, User user, Show show, List<Seat> seats, Payment payment, String bookingStatus) {
        this.bookingId = bookingId;
        this.user = user;
        this.show = show;
        this.seats = seats;
        this.payment = payment;
        this.bookingStatus = bookingStatus;
    }

    public boolean createBooking() {
        if (payment.makePayment()) {
            seats.forEach(Seat::reserveSeat);
            bookingStatus = "Confirmed";
            return true;
        }
        return false;
    }

    public boolean cancelBooking() {
        if (bookingStatus.equals("Confirmed")) {
            seats.forEach(Seat::releaseSeat);
            payment.refundPayment();
            bookingStatus = "Cancelled";
            return true;
        }
        return false;
    }
}

class Payment {
    private String paymentId;
    private double amount;
    private String paymentStatus;

    public Payment(String paymentId, double amount) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentStatus = "Pending";
    }

    public boolean makePayment() {
        // Simulating payment processing
        paymentStatus = "Completed";
        return true;
    }

    public boolean refundPayment() {
        if (paymentStatus.equals("Completed")) {
            paymentStatus = "Refunded";
            return true;
        }
        return false;
    }
}

class User {
    private String userId;
    private String name;
    private String email;
    private String phone;

    public User(String userId, String name, String email, String phone) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getUserDetails() {
        return "User ID: " + userId + ", Name: " + name + ", Email: " + email + ", Phone: " + phone;
    }
}


public class MovieBookingSystem {
    private List<Movie> movies;
    private List<Show> shows;

    public MovieBookingSystem(List<Movie> movies, List<Show> shows) {
        this.movies = movies;
        this.shows = shows;
    }

    public void searchMovies() {
        movies.forEach(movie -> System.out.println(movie.getMovieDetails()));
    }

    public Show selectShow(String showId) {
        return shows.stream().filter(show -> show.getShowId().equals(showId)).findFirst().orElse(null);
    }

    public boolean chooseSeats(Show show, List<Seat> seats) {
        for (Seat seat : seats) {
            if (!seat.isAvailable()) {
                return false; // Seat is not available
            }
        }
        return true;
    }

    public Booking makeBooking(User user, Show show, List<Seat> seats, Payment payment) {
        if (chooseSeats(show, seats)) {
            Booking booking = new Booking("BKG-" + System.currentTimeMillis(), user, show, seats, payment, "Pending");
            if (booking.createBooking()) {
                return booking;
            }
        }
        return null;
    }

    public boolean cancelBooking(Booking booking) {
        return booking.cancelBooking();
    }
}








