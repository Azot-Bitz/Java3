package dev.astamur.geekbrains.lessons.lesson1;

public class Track implements Obstacle {
    private final double distance;

    public Track(double distance) {
        this.distance = distance;
    }

    public boolean perform(Athlete athlete) {
        return athlete.run(distance);
    }

    @Override
    public String toString() {
        return "Track " + distance;
    }
}
