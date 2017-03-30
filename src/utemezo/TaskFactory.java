package utemezo;

/**
 * Created by marci on 2017.03.30..
 */
public class TaskFactory {
    public Task createTask(String id, int priority, int startTime, int burstLength) {
        if(priority == 0) {
            return new RoundRobinScheduledTask(id, startTime, burstLength);
        } else {
            return new ShortestJobFirstScheduledTask(id, startTime, burstLength);
        }
    }
}
