package utemezo;

/**
 * Created by marci on 2017.03.29..
 */
public class Task {
    private String id;
    private int startTime;
    private int burstLength;
    private int priority;
    protected int remainingCycles;

    public Task(String id, int priority, int startTime, int burstLength) {
        this.id = id;
        this.startTime = startTime;
        this.remainingCycles = this.burstLength = burstLength;
        this.priority = priority;
    }

    public String toString() {
        return id + "," + Integer.toString(getPriority()) + "," + startTime + "," + burstLength;
    }

    public int getPriority() {
        return priority;
    }

    public boolean isRunning(int cycle) {
        return cycle >= startTime && !isDone();
    }

    public boolean isDone() {
        return remainingCycles <= 0;
    }

    public int getRemainingCycles() {
        return remainingCycles;
    }

    public void run() {
        System.out.println(toString()+" ran");
        remainingCycles--;
    }
}
