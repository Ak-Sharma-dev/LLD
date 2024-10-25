import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Item {
    private String name;
    private double price;

    public Item(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class Inventory {
    private Map<Item, Integer> items = new HashMap<>();

    public void addItem(Item item, int quantity) {
        items.put(item, items.getOrDefault(item, 0) + quantity);
    }

    public boolean removeItem(Item item) {
        if (!items.containsKey(item) || items.get(item) <= 0) {
            return false;
        }
        items.put(item, items.get(item) - 1);
        return true;
    }

    public int getItemCount(Item item) {
        return items.getOrDefault(item, 0);
    }

    public Item getItem(String itemName) {
        for (Item item : items.keySet()) {
            if (item.getName().equals(itemName)) {
                return item;
            }
        }
        return null;
    }
}

enum Coin {
    PENNY(0.01), NICKEL(0.05), DIME(0.10), QUARTER(0.25), DOLLAR(1.00);

    private double value;

    Coin(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}


class Transaction {
    private Item item;
    private List<Coin> insertedCoins = new ArrayList<>();

    public Transaction(Item item) {
        this.item = item;
    }

    public void addCoin(Coin coin) {
        insertedCoins.add(coin);
    }

    public double getTotal() {
        return insertedCoins.stream().mapToDouble(Coin::getValue).sum();
    }

    public double getChange() {
        return getTotal() - item.getPrice();
    }

    public Item getItem() {
        return item;
    }
}

interface State {
    void handle(VendingMachine vendingMachine);
}

class VendingMachine {
    private Inventory inventory = new Inventory();
    private State currentState;
    private Transaction currentTransaction;

    public void selectItem(String itemName) {
        Item item = inventory.getItem(itemName);
        if (item != null) {
            currentTransaction = new Transaction(item);
            setState(new ItemSelectedState());
        }
    }

    public void insertCoin(Coin coin) {
        if (currentTransaction != null) {
            currentTransaction.addCoin(coin);
            if (currentTransaction.getTotal() >= currentTransaction.getItem().getPrice()) {
                setState(new DispensingState());
            } else {
                setState(new WaitingForMoneyState());
            }
        }
    }

    public void cancelTransaction() {
        if (currentTransaction != null) {
            currentTransaction = null;
            setState(new IdleState());
        }
    }

    public void dispenseItem() {
        if (currentTransaction != null && currentTransaction.getChange() >= 0) {
            inventory.removeItem(currentTransaction.getItem());
            // Dispense item and change
            currentTransaction = null;
            setState(new IdleState());
        }
    }

    public void setState(State state) {
        currentState = state;
        currentState.handle(this);
    }

    public State getCurrentState() {
        return currentState;
    }
}

class IdleState implements State {
    @Override
    public void handle(VendingMachine vendingMachine) {
        System.out.println("Machine is idle. Awaiting item selection.");
    }
}

class ItemSelectedState implements State {
    @Override
    public void handle(VendingMachine vendingMachine) {
        System.out.println("Item selected. Awaiting coin insertion.");
    }
}

class WaitingForMoneyState implements State {
    @Override
    public void handle(VendingMachine vendingMachine) {
        System.out.println("Waiting for more coins.");
    }
}

class DispensingState implements State {
    @Override
    public void handle(VendingMachine vendingMachine) {
        System.out.println("Dispensing item.");
        vendingMachine.dispenseItem();
    }
}






