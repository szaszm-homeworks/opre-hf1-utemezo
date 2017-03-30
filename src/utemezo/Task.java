package utemezo;

/**
 * Created by marci on 2017.03.29..
 */
public abstract class Task {
    private String id;
    private int startTime;
    private int burstLength;

    public Task(String id, int startTime, int burstLength) {
        this.id = id;
        this.startTime = startTime;
        this.burstLength = burstLength;
    }

    public int getStartTime() {
        return startTime;
    }

    public String toString() {
        return id + "," + Integer.toString(getPriority()) + "," + startTime + "," + burstLength;
    }

    abstract int getPriority();
}
