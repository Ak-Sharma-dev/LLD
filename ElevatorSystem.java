import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

enum ElevatorState {
    MOVING,
    IDLE,
    MAINTENANCE
}

enum Direction {
    UP,
    DOWN,
    IDLE
}

class Request {
    private int floor;
    private Direction direction;

    public Request(int floor, Direction direction) {
        this.floor = floor;
        this.direction = direction;
    }

    public int getFloor() {
        return floor;
    }

    public Direction getDirection() {
        return direction;
    }
}

class Elevator {
    private int currentFloor;
    private TreeSet<Integer> targetFloors;
    private Direction direction;
    private ElevatorState state;

    public Elevator() {
        this.currentFloor = 0;
        this.targetFloors = new TreeSet<>();
        this.direction = Direction.IDLE;
        this.state = ElevatorState.IDLE;
    }

    public void addRequest(int floor) {
        targetFloors.add(floor);
        if (state == ElevatorState.IDLE) {
            state = ElevatorState.MOVING;
            direction = (floor > currentFloor) ? Direction.UP : Direction.DOWN;
        }
    }

    public void step() {
        if (targetFloors.isEmpty()) {
            state = ElevatorState.IDLE;
            direction = Direction.IDLE;
            return;
        }

        if (direction == Direction.UP) {
            currentFloor++;
            if (targetFloors.contains(currentFloor)) {
                targetFloors.remove(currentFloor);
            }
        } else if (direction == Direction.DOWN) {
            currentFloor--;
            if (targetFloors.contains(currentFloor)) {
                targetFloors.remove(currentFloor);
            }
        }

        if (targetFloors.isEmpty()) {
            state = ElevatorState.IDLE;
            direction = Direction.IDLE;
        } else {
            direction = (targetFloors.first() > currentFloor) ? Direction.UP : Direction.DOWN;
        }
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public Direction getDirection() {
        return direction;
    }

    public ElevatorState getState() {
        return state;
    }
}

class ElevatorController {
    private List<Elevator> elevators;

    public ElevatorController(int numElevators) {
        elevators = new ArrayList<>(numElevators);
        for (int i = 0; i < numElevators; i++) {
            elevators.add(new Elevator());
        }
    }

    public void addRequest(Request request) {
        Elevator bestElevator = findBestElevator(request);
        bestElevator.addRequest(request.getFloor());
    }

    private Elevator findBestElevator(Request request) {
        Elevator bestElevator = null;
        int minDistance = Integer.MAX_VALUE;

        for (Elevator elevator : elevators) {
            int distance = Math.abs(elevator.getCurrentFloor() - request.getFloor());
            if (distance < minDistance) {
                minDistance = distance;
                bestElevator = elevator;
            }
        }

        return bestElevator;
    }

    public void step() {
        for (Elevator elevator : elevators) {
            elevator.step();
        }
    }

    public List<Elevator> getElevators() {
        return elevators;
    }
}







