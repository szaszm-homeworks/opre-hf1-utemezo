package utemezo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        ArrayList<Task> tasks = new ArrayList<>();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Stream<String> lines = br.lines();
        lines.forEach(x -> {
            if(Objects.equals(x, "")) return;
            String[] parts = x.split(",");
            if(parts.length != 4) return;
            String id = parts[0];
            int priority = Integer.parseInt(parts[1]);
            int startTime = Integer.parseInt(parts[2]);
            int burstLength = Integer.parseInt(parts[3]);

            tasks.add(new Task(id, priority, startTime, burstLength));
        });

        // HF pelda
//        tasks.add(new Task("A", 0, 0, 6));
//        tasks.add(new Task("B", 0, 1, 5));
//        tasks.add(new Task("C", 1, 5, 2));
//        tasks.add(new Task("D", 1, 10, 1));

        // RR/SJF pelda
//        tasks.add(new Task("T1", 0, 0, 6));
//        tasks.add(new Task("T2", 0, 0, 3));
//        tasks.add(new Task("T3", 0, 0, 3));

        // SRTF pelda
//        tasks.add(new Task("T1", 1, 0, 8));
//        tasks.add(new Task("T2", 1, 1, 5));
//        tasks.add(new Task("T3", 1, 2, 9));
//        tasks.add(new Task("T4", 1, 3, 5));

        //Scheduler scheduler = new RoundRobinScheduler(2);
        //Scheduler scheduler = new ShortestJobFirstScheduler();

        EventDispatcher dispatcher = new EventDispatcher();
        List<String> runs = new ArrayList<>();
        dispatcher.listen(TickEvent.class, new EventListener<TickEvent>() {
            @Override
            public void handleEvent(TickEvent event) {
                Task task = event.getTask();
                if(runs.size() == 0 || !Objects.equals(runs.get(runs.size() - 1), task.toString())) {
                    runs.add(task.toString());
                }
            }
        });

        Scheduler scheduler = new StaticMultiLayerScheduler(2, dispatcher);
        tasks.forEach(scheduler::addTask);

        scheduler.run();

        for(String tid: runs) {
            System.out.print(tid);
        }

        System.out.println();

        for(int i = 0; i < tasks.size() - 1; ++i) {
            System.out.print(tasks.get(i).toString() + ":" + tasks.get(i).getWaitingCycles() + ",");
        }
        if(tasks.size() > 0) {
            System.out.print(tasks.get(tasks.size() - 1).toString() + ":" + tasks.get(tasks.size() - 1).getWaitingCycles());
        }
    }
}
