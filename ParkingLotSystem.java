import java.util.ArrayList;
import java.util.List;

class ParkingLot {
    private List<ParkingLevel> levels;

    public ParkingLot(int numberOfLevels, int spotsPerLevel) {
        levels = new ArrayList<>(numberOfLevels);
        for (int i = 0; i < numberOfLevels; i++) {
            levels.add(new ParkingLevel(i, spotsPerLevel));
        }
    }

    public boolean parkVehicle(Vehicle vehicle) {
        for (ParkingLevel level : levels) {
            if (level.parkVehicle(vehicle)) {
                return true;
            }
        }
        return false;
    }

    public boolean unparkVehicle(ParkingTicket ticket) {
        return levels.get(ticket.getLevel()).unparkVehicle(ticket);
    }
}

class ParkingLevel {
    private int floor;
    private ParkingSpot[] spots;
    private int availableSpots;

    public ParkingLevel(int floor, int numberOfSpots) {
        this.floor = floor;
        this.spots = new ParkingSpot[numberOfSpots];
        this.availableSpots = numberOfSpots;
        for (int i = 0; i < numberOfSpots; i++) {
            spots[i] = new ParkingSpot(floor, i);
        }
    }

    public boolean parkVehicle(Vehicle vehicle) {
        if (availableSpots <= 0) return false;
        for (ParkingSpot spot : spots) {
            if (spot.park(vehicle)) {
                availableSpots--;
                return true;
            }
        }
        return false;
    }

    public boolean unparkVehicle(ParkingTicket ticket) {
        int spotNumber = ticket.getSpotNumber();
        if (spots[spotNumber].unpark()) {
            availableSpots++;
            return true;
        }
        return false;
    }
}

class ParkingSpot {
    private int level;
    private int spotNumber;
    private Vehicle currentVehicle;

    public ParkingSpot(int level, int spotNumber) {
        this.level = level;
        this.spotNumber = spotNumber;
    }

    public boolean park(Vehicle vehicle) {
        if (currentVehicle == null) {
            currentVehicle = vehicle;
            return true;
        }
        return false;
    }

    public boolean unpark() {
        if (currentVehicle != null) {
            currentVehicle = null;
            return true;
        }
        return false;
    }
}


abstract class Vehicle {
    protected String licensePlate;
    protected VehicleSize size;

    public VehicleSize getSize() {
        return size;
    }
}

class Car extends Vehicle {
    public Car(String licensePlate) {
        this.licensePlate = licensePlate;
        this.size = VehicleSize.COMPACT;
    }
}

class Truck extends Vehicle {
    public Truck(String licensePlate) {
        this.licensePlate = licensePlate;
        this.size = VehicleSize.LARGE;
    }
}

class Motorcycle extends Vehicle {
    public Motorcycle(String licensePlate) {
        this.licensePlate = licensePlate;
        this.size = VehicleSize.SMALL;
    }
}

enum VehicleSize {
    SMALL, COMPACT, LARGE
}

class ParkingTicket {
    private int level;
    private int spotNumber;
    private String licensePlate;
    private long timestamp;

    public ParkingTicket(int level, int spotNumber, String licensePlate) {
        this.level = level;
        this.spotNumber = spotNumber;
        this.licensePlate = licensePlate;
        this.timestamp = System.currentTimeMillis();
    }

    public int getLevel() {
        return level;
    }

    public int getSpotNumber() {
        return spotNumber;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
