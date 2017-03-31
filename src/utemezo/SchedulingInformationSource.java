package utemezo;

/**
 * Created by marci on 2017.03.31..
 */
public interface SchedulingInformationSource {
    boolean hasHigherPriorityTask(Task task, int cycle);
}
