import java.util.*;

// Car class to store details about each car
class Car {
    private String make;
    private String model;
    private int year;
    private String licensePlateNumber;
    private double rentalPricePerDay;
    private boolean isAvailable;

    // Constructor, getters, and setters
    public Car(String make, String model, int year, String licensePlateNumber, double rentalPricePerDay) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.licensePlateNumber = licensePlateNumber;
        this.rentalPricePerDay = rentalPricePerDay;
        this.isAvailable = true;
    }

    public String getMake() { return make; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public String getLicensePlateNumber() { return licensePlateNumber; }
    public double getRentalPricePerDay() { return rentalPricePerDay; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }
}

// Customer class to store customer information
class Customer {
    private String name;
    private String contactDetails;
    private String driversLicense;

    // Constructor, getters, and setters
    public Customer(String name, String contactDetails, String driversLicense) {
        this.name = name;
        this.contactDetails = contactDetails;
        this.driversLicense = driversLicense;
    }

    public String getName() { return name; }
    public String getContactDetails() { return contactDetails; }
    public String getDriversLicense() { return driversLicense; }
}

// Reservation class to handle reservations
class Reservation {
    private Car car;
    private Customer customer;
    private Date startDate;
    private Date endDate;
    private double totalPrice;

    // Constructor, getters, and setters
    public Reservation(Car car, Customer customer, Date startDate, Date endDate) {
        this.car = car;
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = car.getRentalPricePerDay() * getNumberOfDays();
    }

    public Car getCar() { return car; }
    public Customer getCustomer() { return customer; }
    public Date getStartDate() { return startDate; }
    public Date getEndDate() { return endDate; }
    public double getTotalPrice() { return totalPrice; }

    public int getNumberOfDays() {
        long diff = endDate.getTime() - startDate.getTime();
        return (int) (diff / (1000 * 60 * 60 * 24));
    }
}

// Payment class to handle payment processing
class Payment {
    private Reservation reservation;
    private String paymentMethod;
    private boolean isPaid;

    // Constructor, getters, and setters
    public Payment(Reservation reservation, String paymentMethod) {
        this.reservation = reservation;
        this.paymentMethod = paymentMethod;
        this.isPaid = false;
    }

    public Reservation getReservation() { return reservation; }
    public String getPaymentMethod() { return paymentMethod; }
    public boolean isPaid() { return isPaid; }
    public void setPaid(boolean paid) { isPaid = paid; }

    public boolean processPayment() {
        // Implement payment processing logic here
        // If successful, set isPaid to true and return true
        // Otherwise, return false
        isPaid = true; // For now, we assume payment is always successful
        return isPaid;
    }
}

// CarRentalSystem class to manage the car rental system
public class CarRentalSystem {
    private List<Car> cars;
    private List<Customer> customers;
    private List<Reservation> reservations;

    public CarRentalSystem() {
        this.cars = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.reservations = new ArrayList<>();
    }

    // Add car to the system
    public void addCar(Car car) {
        cars.add(car);
    }

    // Add customer to the system
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    // Create a new reservation
    public Reservation createReservation(Customer customer, Car car, Date startDate, Date endDate) {
        if (car.isAvailable()) {
            Reservation reservation = new Reservation(car, customer, startDate, endDate);
            reservations.add(reservation);
            car.setAvailable(false);
            return reservation;
        } else {
            System.out.println("Car is not available for the selected dates.");
            return null;
        }
    }

    // Cancel a reservation
    public void cancelReservation(Reservation reservation) {
        reservations.remove(reservation);
        reservation.getCar().setAvailable(true);
    }

    // Search for available cars based on criteria
    public List<Car> searchCars(String make, String model, int year, double minPrice, double maxPrice) {
        List<Car> result = new ArrayList<>();
        for (Car car : cars) {
            if (car.isAvailable() &&
                    (make == null || car.getMake().equals(make)) &&
                    (model == null || car.getModel().equals(model)) &&
                    (year == 0 || car.getYear() == year) &&
                    (car.getRentalPricePerDay() >= minPrice && car.getRentalPricePerDay() <= maxPrice)) {
                result.add(car);
            }
        }
        return result;
    }

    // Get all reservations
    public List<Reservation> getReservations() {
        return reservations;
    }

    // Get all cars
    public List<Car> getCars() {
        return cars;
    }

    // Get all customers
    public List<Customer> getCustomers() {
        return customers;
    }
}
