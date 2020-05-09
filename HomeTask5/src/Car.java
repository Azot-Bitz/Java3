import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class Car  implements Runnable{
    private static int CARS_COUNT;
    static {
        CARS_COUNT = 0;
    }
    CyclicBarrier cbar;
    private Race race;
    private int speed;
    private String name;
    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(CyclicBarrier c, Race race, int speed) {
        this.race = race;
        cbar = c;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }
    @Override
    public void run() {

        try {

            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
            cbar.await();


        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);

        }
    }


}
