import java.util.*;
import java.util.concurrent.*;

public class HotelManagementService {
    private final Map<String, Guest> guests = new ConcurrentHashMap<>();
    private final List<Room> rooms = new CopyOnWriteArrayList<>();
    private final List<Reservation> reservations = new CopyOnWriteArrayList<>();
    private final Billing billing = new Billing();

    public void addGuest(Guest guest) {
        guests.put(guest.getId(), guest);
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void makeReservation(Reservation reservation) {
        reservations.add(reservation);
        reservation.getRoom().setAvailable(false);
        Guest guest = reservation.getGuest();
        guest.addReservation(reservation);
    }

    public void checkIn(String reservationId) {
        Reservation reservation = findReservationById(reservationId);
        if (reservation != null) {
            reservation.setStatus(Status.CHECKED_IN);
        }
    }

    public void checkOut(String reservationId) {
        Reservation reservation = findReservationById(reservationId);
        if (reservation != null) {
            reservation.setStatus(Status.CHECKED_OUT);
            reservation.getRoom().setAvailable(true);
            generateBill(reservationId);
        }
    }

    public Bill generateBill(String reservationId) {
        Reservation reservation = findReservationById(reservationId);
        if (reservation != null) {
            return billing.generateBill(reservation);
        }
        return null;
    }

    public Report generateReport() {
        double totalRevenue = billing.calculateTotalRevenue();
        double occupancyRate = (double) reservations.stream().filter(r -> r.getStatus() == Status.CHECKED_IN).count() / rooms.size();
        return new Report(reservations, totalRevenue, occupancyRate);
    }

    private Reservation findReservationById(String reservationId) {
        return reservations.stream().filter(r -> r.getId().equals(reservationId)).findFirst().orElse(null);
    }
}

class Guest {
    private final String id;
    private final String name;
    private final String contactInfo;
    private final List<Reservation> reservations = new ArrayList<>();

    public Guest(String id, String name, String contactInfo) {
        this.id = id;
        this.name = name;
        this.contactInfo = contactInfo;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}

class Room {
    private final String roomNumber;
    private final RoomType type;
    private boolean isAvailable;
    private final double rate;

    public Room(String roomNumber, RoomType type, double rate) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.rate = rate;
        this.isAvailable = true;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public RoomType getType() {
        return type;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public double getRate() {
        return rate;
    }
}

class Reservation {
    private final String id;
    private final Guest guest;
    private final Room room;
    private final Date checkInDate;
    private final Date checkOutDate;
    private Status status;

    public Reservation(String id, Guest guest, Room room, Date checkInDate, Date checkOutDate) {
        this.id = id;
        this.guest = guest;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = Status.RESERVED;
    }

    public String getId() {
        return id;
    }

    public Guest getGuest() {
        return guest;
    }

    public Room getRoom() {
        return room;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

class Billing {
    private final List<Bill> bills = new ArrayList<>();

    public Bill generateBill(Reservation reservation) {
        double amount = reservation.getRoom().getRate() * (reservation.getCheckOutDate().getTime() - reservation.getCheckInDate().getTime()) / (1000 * 60 * 60 * 24);
        Bill bill = new Bill(UUID.randomUUID().toString(), reservation, amount, PaymentStatus.UNPAID);
        bills.add(bill);
        return bill;
    }

    public double calculateTotalRevenue() {
        return bills.stream().filter(bill -> bill.getPaymentStatus() == PaymentStatus.PAID).mapToDouble(Bill::getAmount).sum();
    }
}

class Bill {
    private final String id;
    private final Reservation reservation;
    private final double amount;
    private PaymentStatus paymentStatus;

    public Bill(String id, Reservation reservation, double amount, PaymentStatus paymentStatus) {
        this.id = id;
        this.reservation = reservation;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
    }

    public String getId() {
        return id;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public double getAmount() {
        return amount;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}

class Payment {
    public boolean pay(double amount) {
        // Implement payment logic here
        return true;
    }
}


enum PaymentStatus {
    PAID, UNPAID
}

enum RoomType {
    SINGLE, DOUBLE, DELUXE, SUITE
}

enum Status {
    RESERVED, CHECKED_IN, CHECKED_OUT
}

class Report {
    private final List<Reservation> reservations;
    private final double totalRevenue;
    private final double occupancyRate;

    public Report(List<Reservation> reservations, double totalRevenue, double occupancyRate) {
        this.reservations = reservations;
        this.totalRevenue = totalRevenue;
        this.occupancyRate = occupancyRate;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public double getOccupancyRate() {
        return occupancyRate;
    }
}



