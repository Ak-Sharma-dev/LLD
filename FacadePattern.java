package design.patterns;

class FlightBooking {
    public void bookFlight(String from, String to) {
        System.out.println("Flight booked from " + from + " to " + to);
    }
}

class HotelBooking {
    public void bookHotel(String city) {
        System.out.println("Hotel booked in " + city);
    }
}

class CarRental {
    public void rentCar(String city) {
        System.out.println("Car rented in " + city);
    }
}


class BookingFacade {

    //The Facade pattern provides a unified interface to a set of interfaces in a subsystem.
    //It defines a higher-level interface that makes the subsystem easier to use.
    private FlightBooking flightBooking;
    private HotelBooking hotelBooking;
    private CarRental carRental;

    public BookingFacade() {
        this.flightBooking = new FlightBooking();
        this.hotelBooking = new HotelBooking();
        this.carRental = new CarRental();
    }

    public void bookCompletePackage(String from, String to, String city) {
        flightBooking.bookFlight(from, to);
        hotelBooking.bookHotel(city);
        carRental.rentCar(city);
    }
}

public class FacadePattern {
    public static void main(String[] args) {

    }
}

