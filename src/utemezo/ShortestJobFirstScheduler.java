package utemezo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marci on 2017.03.31..
 */
public class ShortestJobFirstScheduler extends Scheduler {
    boolean done = false;
    private ArrayList<Task> tasks;
    private EventDispatcher dispatcher;

    public ShortestJobFirstScheduler(EventDispatcher dispatcher) {
        super();
        tasks = new ArrayList<>();
        this.dispatcher = dispatcher;
    }

    @Override
    public void addTask(Task task) {
        tasks.add(task);
    }

    @Override
    public int tick(int cycle) {
        int cyclesRan = 0;
        if(done) return 1;
        Task task = null;
        for(Task t: tasks) {
            if(t.isRunning(cycle) && !(task != null && t.getRemainingCycles() >= task.getRemainingCycles())) {
                task = t;
            }
        }

        if(task == null) {
            done = true;
            return 1;
        }

        while (!task.isDone()) {
            task.run();
            dispatcher.dispatch(new TickEvent(task, cycle));
            cyclesRan++;
            cycle++;
        }

        tasks.remove(task);
        if(tasks.size() == 0) {
            done = true;
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

    @Override
    protected List<Task> getWaitingTasks(Task runningTask, int cycle) {
        List<Task> waiting = new ArrayList<>();
        for(Task task: tasks) {
            if(task.isRunning(cycle) && task != runningTask) {
                waiting.add(task);
            }
        }

        return waiting;
    }
}
