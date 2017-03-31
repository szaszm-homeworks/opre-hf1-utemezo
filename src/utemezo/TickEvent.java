package utemezo;

/**
 * Created by marci on 2017.03.31..
 */
public class TickEvent {
    private Task taskRan;
    private int cycle;

    public TickEvent(Task taskRan, int cycle) {
        this.taskRan = taskRan;
        this.cycle = cycle;
    }

    public Task getTask() {
        return taskRan;
    }

    public int getCycle() {
        return cycle;
    }
}
