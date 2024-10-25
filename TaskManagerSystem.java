
import java.util.*;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

class Task {
    private String id;
    private String title;
    private String description;
    private Date dueDate;
    private Priority priority;
    private Status status;
    private User assignedUser;
    private Date reminderTime;

    public Task(String id, String title, String description, Date dueDate, Priority priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = Status.PENDING;
    }

    // Getters and setters

    public void setReminder(Date reminderTime) {
        this.reminderTime = reminderTime;
    }

    public void setAssignedUser(User user) {
        this.assignedUser = user;
    }

    public void markAsCompleted() {
        this.status = Status.COMPLETED;
    }

    // Other methods
}

enum Priority {
    LOW, MEDIUM, HIGH
}

enum Status {
    PENDING, IN_PROGRESS, COMPLETED
}

class User {
    private String id;
    private String name;
    private String email;

    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // Getters and setters
}


class TaskManager {
    private List<Task> tasks;
    private Lock lock;

    public TaskManager() {
        this.tasks = new ArrayList<>();
        this.lock = new ReentrantLock();
    }

    public void addTask(Task task) {
        lock.lock();
        try {
            tasks.add(task);
        } finally {
            lock.unlock();
        }
    }

    public void updateTask(Task task) {
        lock.lock();
        try {
            // Update the task in the list
        } finally {
            lock.unlock();
        }
    }

    public void deleteTask(Task task) {
        lock.lock();
        try {
            tasks.remove(task);
        } finally {
            lock.unlock();
        }
    }

    public List<Task> getTasks() {
        return tasks;
    }

    // Other methods to handle task operations
}

class ReminderService {
    private Timer timer;

    public ReminderService() {
        this.timer = new Timer();
    }

    public void setReminder(Task task) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                sendReminder(task);
            }
        }, task.getReminderTime());
    }

    private void sendReminder(Task task) {
        System.out.println("Reminder: Task " + task.getTitle() + " is due soon.");
        // Implementation to send email or notification
    }
}

class SearchService {

    public List<Task> searchTasksByPriority(List<Task> tasks, Priority priority) {
        return tasks.stream()
                .filter(task -> task.getPriority() == priority)
                .collect(Collectors.toList());
    }

    public List<Task> searchTasksByDueDate(List<Task> tasks, Date dueDate) {
        return tasks.stream()
                .filter(task -> task.getDueDate().equals(dueDate))
                .collect(Collectors.toList());
    }

    public List<Task> searchTasksByAssignedUser(List<Task> tasks, User user) {
        return tasks.stream()
                .filter(task -> task.getAssignedUser().equals(user))
                .collect(Collectors.toList());
    }

    // Other search/filter methods
}

class TaskHistory {
    private User user;
    private List<Task> completedTasks;

    public TaskHistory(User user) {
        this.user = user;
        this.completedTasks = new ArrayList<>();
    }

    public void addCompletedTask(Task task) {
        if (task.getStatus() == Status.COMPLETED) {
            completedTasks.add(task);
        }
    }

    public List<Task> getCompletedTasks() {
        return completedTasks;
    }
}


public class TaskManagerSystem {
}
