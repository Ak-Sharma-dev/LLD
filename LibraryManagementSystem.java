// Books, members, and catalog.
// Book search (by title, author, genre).
// Book issue and return functionality.
// Different member types (e.g., student, teacher) with different borrowing limits.
// Fine calculation for late returns.

import java.util.*;
import java.util.stream.Collectors;

class Book {
    private String title;
    private String author;
    private String genre;
    private String isbn;
    private boolean isAvailable;

    public Book(String title, String author, String genre, String isbn) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isbn = isbn;
        this.isAvailable = true;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}


abstract class Member {
    private String memberId;
    private String name;
    private int maxBooksLimit;
    private List<Optional<Book>> borrowedBooks;

    public Member(String memberId, String name, int maxBooksLimit) {
        this.memberId = memberId;
        this.name = name;
        this.maxBooksLimit = maxBooksLimit;
        this.borrowedBooks = new ArrayList<Book>();
    }

    public boolean borrowBook(Book book) {
        if (borrowedBooks.size() >= maxBooksLimit || !book.isAvailable()) {
            return false;
        }
        borrowedBooks.add(book);
        book.setAvailable(false);
        return true;
    }

    public boolean returnBook(Optional<Book> book) {
        if (borrowedBooks.remove(book)) {
            book.setAvailable(true);
            return true;
        }
        return false;
    }

    // Abstract method for fine calculation
     public abstract double calculateFine(int overdueDays);

    // Getters and Setters
    public String getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public List<Optional<Book>> getBorrowedBooks() {
        return borrowedBooks;
    }
}


class Student extends Member {
    private static final int MAX_BOOKS_LIMIT = 5;
    private static final double FINE_PER_DAY = 1.0; // Example fine rate

    public Student(String memberId, String name) {
        super(memberId, name, MAX_BOOKS_LIMIT);
    }

    @Override
    public double calculateFine(int overdueDays) {
        return overdueDays * FINE_PER_DAY;
    }
}

class Teacher extends Member {
    private static final int MAX_BOOKS_LIMIT = 10;
    private static final double FINE_PER_DAY = 0.5; // Example fine rate

    public Teacher(String memberId, String name) {
        super(memberId, name, MAX_BOOKS_LIMIT);
    }

    @Override
    public double calculateFine(int overdueDays) {
        return overdueDays * FINE_PER_DAY;
    }
}


class Catalog {
    private List<Book> books;

    public Catalog() {
        books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public List<Book> searchByTitle(String title) {
        return books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .collect(Collectors.toList());
    }

    public List<Book> searchByAuthor(String author) {
        return books.stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .collect(Collectors.toList());
    }

    public List<Book> searchByGenre(String genre) {
        return books.stream()
                .filter(book -> book.getGenre().equalsIgnoreCase(genre))
                .collect(Collectors.toList());
    }

    public Optional<Book> searchByIsbn(String isbn) {
        return books.stream()
                .filter(book -> book.getIsbn().equalsIgnoreCase(isbn))
                .findAny();
    }
}


public class LibraryManagementSystem {
    private Catalog catalog;
    private Map<String, Member> members;

    public LibraryManagementSystem() {
        catalog = new Catalog();
        members = new HashMap<>();
    }

    public void addBook(Book book) {
        catalog.addBook(book);
    }

    public void registerMember(Member member) {
        members.put(member.getMemberId(), member);
    }

    public boolean issueBook(String memberId, String isbn) {
        Member member = members.get(memberId);
        Optional<Book> book = catalog.searchByIsbn(isbn);
        if (member != null && book != null) {
            return member.borrowBook(book);
        }
        return false;
    }

    public boolean returnBook(String memberId, String isbn) {
        Member member = members.get(memberId);
        Optional<Book> book = catalog.searchByIsbn(isbn);
        if (member != null && book.get() == null) {
            return member.returnBook(book.get());
        }
        return false;
    }

    // Additional methods like calculateFine, searchBooks etc.
}




