package utemezo;

import java.util.List;

/**
 * Created by marci on 2017.03.31..
 */
public class StaticMultiLayerScheduler extends Scheduler implements SchedulingInformationSource {
    RoundRobinScheduler roundRobinScheduler;
    ShortestJobFirstScheduler shortestJobFirstScheduler;

    public StaticMultiLayerScheduler(int roundRobinTimeSliceLength, EventDispatcher dispatcher) {
        this.roundRobinScheduler = new RoundRobinScheduler(roundRobinTimeSliceLength, dispatcher, this);
        this.shortestJobFirstScheduler = new ShortestJobFirstScheduler(dispatcher);
        dispatcher.listen(TickEvent.class, new EventListener<TickEvent>() {
            @Override
            public void handleEvent(TickEvent event) {
                List<Task> waiting = getWaitingTasks(event.getTask(), event.getCycle());
                for(Task task: waiting) {
                    task.waitTick();
                }
            }
        });
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

    @Override
    protected List<Task> getWaitingTasks(Task runningTask, int cycle) {
        List<Task> sjfList = shortestJobFirstScheduler.getWaitingTasks(runningTask, cycle);
        List<Task> rrList = roundRobinScheduler.getWaitingTasks(runningTask, cycle);
        sjfList.addAll(rrList);
        return sjfList;
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
            return 1;
        }
    }

}
