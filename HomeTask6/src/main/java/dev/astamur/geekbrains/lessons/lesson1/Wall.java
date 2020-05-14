package dev.astamur.geekbrains.lessons.lesson1;

public class Wall implements Obstacle {
    private final double height;

    public Wall(double height) {
        this.height = height;
    }

    public boolean perform(Athlete athlete) {
        return athlete.jump(height);
    }

    @Override
    public String toString() {
        return "Wall " + height;
    }
}
