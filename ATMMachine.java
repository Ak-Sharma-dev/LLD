import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;


public class ATMMachine {
    private CardReader cardReader;
    private Keypad keypad;
    private Screen screen;
    private CashDispenser cashDispenser;
    private ReceiptPrinter receiptPrinter;
    private BankServer bankServer;

    public ATMMachine() {
        this.cardReader = new CardReader();
        this.keypad = new Keypad();
        this.screen = new Screen();
        this.cashDispenser = new CashDispenser();
        this.receiptPrinter = new ReceiptPrinter();
        this.bankServer = new BankServer();
    }

    public void start() {
        screen.displayMessage("Welcome to the ATM");
        Card card = cardReader.readCard();
        if (card != null) {
            screen.displayMessage("Please enter your PIN:");
            String pin = keypad.getInput();
            boolean authenticated = bankServer.authenticate(card, pin);
            if (authenticated) {
                boolean exit = false;
                while (!exit) {
                    screen.displayOptions();
                    int choice = Integer.parseInt(keypad.getInput());
                    switch (choice) {
                        case 1:
                            withdraw(card);
                            break;
                        case 2:
                            deposit(card);
                            break;
                        case 3:
                            checkBalance(card);
                            break;
                        case 4:
                            transfer(card);
                            break;
                        case 5:
                            exit = true;
                            break;
                        default:
                            screen.displayMessage("Invalid choice. Please try again.");
                    }
                }
            } else {
                screen.displayMessage("Authentication failed.");
            }
        } else {
            screen.displayMessage("Invalid card.");
        }
        screen.displayMessage("Thank you for using the ATM.");
    }

    private void withdraw(Card card) {
        screen.displayMessage("Enter amount to withdraw:");
        double amount = Double.parseDouble(keypad.getInput());
        if (bankServer.processTransaction(card, TransactionType.WITHDRAW, amount)) {
            cashDispenser.dispenseCash(amount);
            receiptPrinter.printReceipt(TransactionType.WITHDRAW, amount);
        } else {
            screen.displayMessage("Transaction failed. Please check your balance.");
        }
    }

    private void deposit(Card card) {
        screen.displayMessage("Enter amount to deposit:");
        double amount = Double.parseDouble(keypad.getInput());
        if (bankServer.processTransaction(card, TransactionType.DEPOSIT, amount)) {
            receiptPrinter.printReceipt(TransactionType.DEPOSIT, amount);
        } else {
            screen.displayMessage("Transaction failed. Please try again.");
        }
    }

    private void checkBalance(Card card) {
        double balance = bankServer.getBalance(card);
        screen.displayMessage("Your balance is: " + balance);
    }

    private void transfer(Card card) {
        screen.displayMessage("Enter account number to transfer to:");
        String toAccount = keypad.getInput();
        screen.displayMessage("Enter amount to transfer:");
        double amount = Double.parseDouble(keypad.getInput());
        if (bankServer.processTransfer(card, toAccount, amount)) {
            receiptPrinter.printReceipt(TransactionType.TRANSFER, amount);
        } else {
            screen.displayMessage("Transaction failed. Please check your balance or account details.");
        }
    }

    public static void main(String[] args) {
        ATMMachine atm = new ATMMachine();
        atm.start();
    }
}


class BankServer {
    private Map<String, Account> accounts;

    public BankServer() {
        this.accounts = new HashMap<>();
        accounts.put("1234-5678-9876-5432", new Account("1234-5678-9876-5432", 1000.0));
    }

    public boolean authenticate(Card card, String pin) {
        // For simplicity, we'll assume the PIN is always correct.
        // In a real-world scenario, we would validate the PIN.
        return accounts.containsKey(card.getCardNumber());
    }

    public boolean processTransaction(Card card, TransactionType type, double amount) {
        Account account = accounts.get(card.getCardNumber());
        if (type == TransactionType.WITHDRAW && account.getBalance() >= amount) {
            account.setBalance(account.getBalance() - amount);
            return true;
        } else if (type == TransactionType.DEPOSIT) {
            account.setBalance(account.getBalance() + amount);
            return true;
        }
        return false;
    }

    public double getBalance(Card card) {
        return accounts.get(card.getCardNumber()).getBalance();
    }

    public boolean processTransfer(Card fromCard, String toAccountNumber, double amount) {
        Account fromAccount = accounts.get(fromCard.getCardNumber());
        Account toAccount = accounts.get(toAccountNumber);
        if (fromAccount != null && toAccount != null && fromAccount.getBalance() >= amount) {
            fromAccount.setBalance(fromAccount.getBalance() - amount);
            toAccount.setBalance(toAccount.getBalance() + amount);
            return true;
        }
        return false;
    }
}


class CardReader {
    public Card readCard() {
        // For simplicity, we'll assume a card is always read successfully.
        // In a real-world scenario, we would interact with hardware.
        return new Card("1234-5678-9876-5432");
    }
}


class Keypad {
    private Scanner scanner;

    public Keypad() {
        this.scanner = new Scanner(System.in);
    }

    public String getInput() {
        return scanner.nextLine();
    }
}

class Screen {
    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void displayOptions() {
        System.out.println("1. Withdraw");
        System.out.println("2. Deposit");
        System.out.println("3. Check Balance");
        System.out.println("4. Transfer");
        System.out.println("5. Exit");
    }
}

class CashDispenser {
    public void dispenseCash(double amount) {
        System.out.println("Dispensing cash: " + amount);
    }
}

class ReceiptPrinter {
    public void printReceipt(TransactionType type, double amount) {
        System.out.println("Printing receipt: " + type + " of amount " + amount);
    }
}

class Account {
    private String accountNumber;
    private double balance;

    public Account(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}

class Card {
    private String cardNumber;

    public Card(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }
}

enum TransactionType {
    WITHDRAW,
    DEPOSIT,
    TRANSFER
}












