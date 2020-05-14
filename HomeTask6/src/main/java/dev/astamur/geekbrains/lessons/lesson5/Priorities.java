package dev.astamur.geekbrains.lessons.lesson5;

public class Priorities {
    public static void main(String[] args) throws InterruptedException {
        SyncExample.Counter counterA = new SyncExample.Counter();
        SyncExample.Counter counterB = new SyncExample.Counter();

        Thread threadA = new Thread(() -> {
           while (true) {
               counterA.increment();
           }
        });

        Thread threadB = new Thread(() -> {
            while (true) {
                counterB.increment();
            }
        });

        threadA.setPriority(Thread.MAX_PRIORITY);
        threadB.setPriority(Thread.MIN_PRIORITY);
        threadA.start();
        threadB.start();

        Thread.sleep(10_000);

        threadA.interrupt();
        threadB.interrupt();

        System.out.println("Counter A: " + counterA.getValue());
        System.out.println("Counter B: " + counterB.getValue());
    }
}
