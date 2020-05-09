import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {
    Semaphore smp;
    public Tunnel(Semaphore s) {
        smp = s;
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
    }
    @Override
    public void go(Car c) {
        try {

            try {
                smp.acquire();
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                Thread.sleep(500);
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                smp.release();
                System.out.println(c.getName() + " закончил этап: " + description);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}