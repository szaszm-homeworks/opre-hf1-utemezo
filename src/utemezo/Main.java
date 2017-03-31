package utemezo;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
	// write your code here
        ArrayList<Task> tasks = new ArrayList<>();

        // HF pelda
        tasks.add(new Task("A", 0, 0, 6));
        tasks.add(new Task("B", 0, 1, 5));
        tasks.add(new Task("C", 1, 5, 2));
        tasks.add(new Task("D", 1, 10, 1));

        // RR/SJF pelda
//        tasks.add(new Task("T1", 0, 0, 6));
//        tasks.add(new Task("T2", 0, 0, 3));
//        tasks.add(new Task("T3", 0, 0, 3));

        // SRTF pelda
//        tasks.add(new Task("T1", 1, 0, 8));
//        tasks.add(new Task("T2", 1, 1, 5));
//        tasks.add(new Task("T3", 1, 2, 9));
//        tasks.add(new Task("T4", 1, 3, 5));

        tasks.forEach(System.out::println);
        System.out.println();

        //Scheduler scheduler = new RoundRobinScheduler(2);
        //Scheduler scheduler = new ShortestJobFirstScheduler();
        Scheduler scheduler = new StaticMultiLayerScheduler(2);
        tasks.forEach(scheduler::addTask);

        scheduler.run();
    }
}
