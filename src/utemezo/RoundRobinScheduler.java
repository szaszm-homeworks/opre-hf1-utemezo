package utemezo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by marci on 2017.03.30..
 */
public class RoundRobinScheduler extends Scheduler {
    private Queue<Task> runningTasks;
    private int timeSliceLength;
    private SchedulingInformationSource informationSource;
    protected ArrayList<Task> tasks;

    public RoundRobinScheduler(int timeSliceLength) {
        this(timeSliceLength, null);
    }

    public RoundRobinScheduler(int timeSliceLength, SchedulingInformationSource informationSource) {
        super();
        tasks = new ArrayList<>();
        runningTasks = new LinkedList<>();
        this.timeSliceLength = timeSliceLength;
        this.informationSource = informationSource;
    }

    private void syncRunningTasks(int cycle) {
        runningTasks.removeIf(x->!x.isRunning(cycle));
        tasks.forEach(x -> {
            if(x.isRunning(cycle) && !runningTasks.contains(x)) {
                runningTasks.add(x);
            }
        });
    }

    @Override
    public void addTask(Task task) {
        tasks.add(task);
    }

    public int tick(int cycle) {
        int cyclesRan = 0;

        if(!canRun(cycle)) {
            return 0;
        }

        Task task = runningTasks.peek();

        do {
            task.run();
            cyclesRan++;
            cycle++;
            syncRunningTasks(cycle);
        } while (timeSliceLength > cyclesRan && !task.isDone() && !shouldInterrupt(task, cycle));

        // rotate element to the end of the queue
        try {
            runningTasks.add(runningTasks.remove());
        } catch(Throwable ignored) { }

        return cyclesRan;
    }

    @Override
    public boolean isDone() {
        for(Task task: tasks) {
            if(!task.isDone()) return false;
        }

        return true;
    }

    @Override
    public boolean canRun(int cycle) {
        syncRunningTasks(cycle);

        Task task = runningTasks.peek();
        return task != null;
    }

    private boolean shouldInterrupt(Task task, int cycle) {
        return informationSource != null && informationSource.hasHigherPriorityTask(task, cycle);
    }
}
