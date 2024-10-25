import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

class User {
    private int user_id;
    private String user_name;
    private String password;
}

class Points {
    private int points;
    private int user_id;

    public int checkPoints() {}

    public void addPoints() {}

    public void redeemPoints() {}
}

class Activity {
    private int steps;
    private int calories;
    private LocalTime duration;
    private int user_id;
    private LocalDate date;
    private int averageHeartBeat;
}

class Goals {
    private int steps;
    private int calories;
    private int duration;
    private int user_id;

    public void setSteps() {

    }

    public void setCalories() {

    }

    public void setDuration() {

    }
}

class NotificationService {
    public void notifyGoalsAchieved() {

    }

    public void NotifyCloserToGoals() {

    }

    public void NotifyLowActivity() {

    }
}

class History {

    public void weeklyHistory() {}

    public void monthlyHistory() {}

}

public class FitnessAppTracker {

    List<Activity> activities;
    Goals goals;
    User user;
    NotificationService notificationService;
    History history;
    Points points;


    public void setGoals() {}
    public void trackFitnessData(Activity data) {}
    public void notifyUser(String message) {}

}
