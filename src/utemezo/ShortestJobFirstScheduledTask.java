package utemezo;

/**
 * Created by marci on 2017.03.30..
 */
public class ShortestJobFirstScheduledTask extends Task {
    public ShortestJobFirstScheduledTask(String id, int startTime, int burstLength) {
        super(id, startTime, burstLength);
    }

    @Override
    int getPriority() {
        return 1;
    }
}
