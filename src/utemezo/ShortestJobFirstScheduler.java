package utemezo;

import java.util.ArrayList;

/**
 * Created by marci on 2017.03.31..
 */
public class ShortestJobFirstScheduler extends Scheduler {
    boolean done = false;
    protected ArrayList<Task> tasks;

    public ShortestJobFirstScheduler() {
        super();
        tasks = new ArrayList<>();
    }

    @Override
    public void addTask(Task task) {
        tasks.add(task);
    }

    @Override
    public int tick(int cycle) {
        int cyclesRan = 0;
        if(done) return cyclesRan;
        Task task = null;
        for(Task t: tasks) {
            if(t.isRunning(cycle) && !(task != null && t.getRemainingCycles() >= task.getRemainingCycles())) {
                task = t;
            }
        }

        if(task == null) {
            done = true;
            return cyclesRan;
        }

        while (!task.isDone()) {
            task.run();
            cyclesRan++;
            cycle++;
        }

        return cyclesRan;
    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public boolean canRun(int cycle) {
        for(Task task: tasks) {
            if(task.isRunning(cycle)) return true;
        }
        return false;
    }
}
