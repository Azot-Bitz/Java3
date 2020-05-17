package dev.astamur.geekbrains.lessons.lesson1;

public class Cat extends AbstractAthlete {
    public Cat(String name, double maxRunDistance, double maxJumpHeight) {
        super(name, maxRunDistance, maxJumpHeight);
    }

    public boolean jump(double height) {
        if (maxJumpHeight >= height) {
            System.out.printf("Cat '%s' jumped %f\n", name, height);
            return true;
        } else {
            System.out.printf("Cat '%s' failed to jump %f\n", name, height);
            return false;
        }
    }

    public boolean run(double distance) {
        if (maxRunDistance >= distance) {
            System.out.printf("Cat '%s' ran %f\n", name, distance);
            return true;
        } else {
            System.out.printf("Cat '%s' failed to run %f\n", name, distance);
            return false;
        }
    }
}
