/*

    The logging framework should support different log levels, such as DEBUG, INFO, WARNING, ERROR, and FATAL.
    It should allow logging messages with a timestamp, log level, and message content.
    The framework should support multiple output destinations, such as console, file, and database.
    It should provide a configuration mechanism to set the log level and output destination.
    The logging framework should be thread-safe to handle concurrent logging from multiple threads.
    It should be extensible to accommodate new log levels and output destinations in the future.

 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

enum LogLevel {
    DEBUG,
    INFO,
    WARNING,
    ERROR,
    FATAL
}

enum Output {
    CONSOLE,
    FILE,
    DATABASE
}

interface Storage {
    void log(String s);
}

class ConsoleStorage implements Storage {

    @Override
    public void log(String s) {

    }
}

class FileStorage implements Storage {

    String filePath;

    @Override
    public void log(String s) {

    }
}

class DatabaseStorage implements Storage {

    String databaseName;
    String tableName;

    @Override
    public void log(String s) {

    }
}

public class LoggingFramework {
    private static LoggingFramework instance;
    private LogLevel level;
    private List<Storage> destinations;
    private ReentrantLock lock;

    // Private constructor to prevent instantiation
    private LoggingFramework(LogLevel level) {
        this.level = level;
        this.destinations = new ArrayList<>();
        this.lock = new ReentrantLock();
    }

    // Static method to get the single instance of Logger
    public static LoggingFramework getInstance() {
        if (instance == null) {
            synchronized (LoggingFramework.class) {
                if (instance == null) {
                    instance = new LoggingFramework(LogLevel.DEBUG); // Default log level
                }
            }
        }
        return instance;
    }

    public void setLogLevel(LogLevel level) {
        this.level = level;
    }

    public void addDestination(Storage destination) {
        this.destinations.add(destination);
    }

    private void logMessage(LogLevel level, String message) {
        if (this.level.ordinal() <= level.ordinal()) {
            String formattedMessage = formatMessage(level, message);
            lock.lock();
            try {
                for (Storage destination : destinations) {
                    destination.log(formattedMessage);
                }
            } finally {
                lock.unlock();
            }
        }
    }

    private String formatMessage(LogLevel level, String message) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("[%s] [%s] %s", now.format(formatter), level.name(), message);
    }

    public void logDebug(String message) {
        logMessage(LogLevel.DEBUG, message);
    }

    public void logInfo(String message) {
        logMessage(LogLevel.INFO, message);
    }

    public void logWarning(String message) {
        logMessage(LogLevel.WARNING, message);
    }

    public void logError(String message) {
        logMessage(LogLevel.ERROR, message);
    }

    public void logFatal(String message) {
        logMessage(LogLevel.FATAL, message);
    }
}
