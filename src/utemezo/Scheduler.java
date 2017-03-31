package utemezo;

/**
 * Created by marci on 2017.03.31..
 */
public abstract class Scheduler {

    protected Scheduler() {
    }

    public abstract void addTask(Task task);

    public abstract int tick(int cycle);

    public abstract boolean isDone();

    public abstract boolean canRun(int cycle);

    public void run() {
        int cycle = 0;
        while(!isDone()) {
            cycle += tick(cycle);
        }
    }
}
