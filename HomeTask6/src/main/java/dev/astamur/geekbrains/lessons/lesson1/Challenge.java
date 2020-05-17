package dev.astamur.geekbrains.lessons.lesson1;

import java.util.Arrays;

public class Challenge {
    public static void main(String[] args) {
        Athlete[] athletes = new Athlete[]{
                new Human("Luke", 5000, 1.5),
                new Robot("C3PO", 1000, 0.5),
                new Cat("Tom", 3000, 1),
        };

        boolean[] access = new boolean[athletes.length];
        Arrays.fill(access, true);

        Obstacle[] obstacles = new Obstacle[]{
                new Track(500),
                new Wall(0.5),
                new Track(1500),
                new Wall(1),
                new Track(5000)
        };

        for (Obstacle obstacle : obstacles) {
            System.out.printf("\nObstacle: %s\n", obstacle);

            for (int i = 0; i < athletes.length; i++) {
                if (access[i] && !obstacle.perform(athletes[i])) {
                    System.out.printf("DQF. Athlete '%s'\n", athletes[i].getName());
                    access[i] = false;
                }
            }
        }

        System.out.println("\n --- Results ---");
        for (int i = 0; i < athletes.length; i++) {
            if (access[i]) {
                System.out.printf("Athlete '%s' finished\n", athletes[i].getName());
            }
        }
    }
}
