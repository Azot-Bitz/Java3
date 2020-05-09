import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Road2 extends Stage {
    Semaphore smp;
    boolean b = true;
    CyclicBarrier cb;
    public Road2(int length, Semaphore semaphore, CyclicBarrier cyclicBarrier) {
        smp = semaphore;
        cb = cyclicBarrier;
        this.length = length;
        this.description = "Дорога " + length + " метров";
    }
    @Override
    public void go(Car c) {
        try {
            System.out.println(c.getName() + " начал этап: " + description);
            Thread.sleep(length / c.getSpeed() * 1000);
            System.out.println(c.getName() + " закончил этап: " + description);
            smp.acquire();
            if(b) {
                System.out.println("Победил - " + c.getName());
                b = false;
            }
            smp.release();
            cb.await();

        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();


        }
    }
}