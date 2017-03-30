package utemezo;

/**
 * Created by marci on 2017.03.30..
 */
public class RoundRobinScheduledTask extends Task{
    public RoundRobinScheduledTask(String id, int startTime, int burstLength) {
        super(id, startTime, burstLength);
    }

    @Override
    int getPriority() {
        return 0;
    }
}
