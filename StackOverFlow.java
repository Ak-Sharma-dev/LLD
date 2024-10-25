import java.time.LocalDateTime;
import java.util.List;

class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private int reputation;

    // Constructors, getters, and setters

    public void register() {
        // Registration logic
    }

    public void login() {
        // Login logic
    }

    public void logout() {
        // Logout logic
    }
}

class Question {
    private int id;
    private String title;
    private String body;
    private List<Tag> tags;
    private User author;
    private int votes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors, getters, and setters

    public void post() {
        // Posting logic
    }

    public void update() {
        // Update logic
    }

    public void delete() {
        // Delete logic
    }
}

class Answer {
    private int id;
    private int questionId;
    private String body;
    private User author;
    private int votes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors, getters, and setters

    public void post() {
        // Posting logic
    }

    public void update() {
        // Update logic
    }

    public void delete() {
        // Delete logic
    }
}

class Comment {
    private int id;
    private int parentId;
    private String body;
    private User author;
    private LocalDateTime createdAt;

    // Constructors, getters, and setters

    public void post() {
        // Posting logic
    }

    public void update() {
        // Update logic
    }

    public void delete() {
        // Delete logic
    }
}

class Tag {
    private int id;
    private String name;
    private List<Question> questions;

    // Constructors, getters, and setters
}

class Vote {
    private int id;
    private int userId;
    private int parentId;
    private VoteType voteType;
    private LocalDateTime createdAt;

    // Constructors, getters, and setters

    public void vote() {
        // Voting logic
    }
}

enum VoteType {
    UPVOTE,
    DOWNVOTE
}

class Reputation {
    private int userId;
    private int points;

    // Constructors, getters, and setters

    public void update() {
        // Update reputation logic
    }
}
