package utemezo;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
	// write your code here
        ArrayList<Task> tasks = new ArrayList<>();
        TaskFactory factory = new TaskFactory();
        tasks.add(factory.createTask("A", 0, 0, 6));
        tasks.add(factory.createTask("B", 0, 1, 5));
        tasks.add(factory.createTask("C", 1, 5, 2));
        tasks.add(factory.createTask("D", 1, 10, 1));

        tasks.forEach(System.out::println);
    }
}
