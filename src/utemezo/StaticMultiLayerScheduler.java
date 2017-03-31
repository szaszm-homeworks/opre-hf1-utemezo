package utemezo;

/**
 * Created by marci on 2017.03.31..
 */
public class StaticMultiLayerScheduler extends Scheduler implements SchedulingInformationSource {
    RoundRobinScheduler roundRobinScheduler;
    ShortestJobFirstScheduler shortestJobFirstScheduler;

    public StaticMultiLayerScheduler(int roundRobinTimeSliceLength) {
        this.roundRobinScheduler = new RoundRobinScheduler(roundRobinTimeSliceLength, this);
        this.shortestJobFirstScheduler = new ShortestJobFirstScheduler();
    }

    @Override
    public boolean hasHigherPriorityTask(Task task, int cycle) {
        return task.getPriority() != 1 && shortestJobFirstScheduler.canRun(cycle);
    }

    public boolean isDone() {
        return shortestJobFirstScheduler.isDone() && roundRobinScheduler.isDone();
    }

    @Override
    public boolean canRun(int cycle) {
        return shortestJobFirstScheduler.canRun(cycle) || roundRobinScheduler.canRun(cycle);
    }

    public void addTask(Task task) {
        if(task.getPriority() == 1) {
            shortestJobFirstScheduler.addTask(task);
        } else {
            roundRobinScheduler.addTask(task);
        }
    }

    @Override
    public int tick(int cycle) {
        if(shortestJobFirstScheduler.canRun(cycle)) {
            return shortestJobFirstScheduler.tick(cycle);
        } else if(roundRobinScheduler.canRun(cycle)) {
            return roundRobinScheduler.tick(cycle);
        } else {
            return 0;
        }
    }

}
