package dev.astamur.geekbrains.lessons.lesson5;

import java.util.Map;

public class DaemonExample {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Main thread: " + Thread.currentThread().getName());

        Thread additional = new Thread(new Task("Hello!"));

        //MyTread additional = new MyTread();
        //additional.setDaemon(true);
        additional.start();

        System.out.println("Bye, main: " + Thread.currentThread().getName());

        Thread.sleep(5000);

        additional.interrupt();
    }
}

class MyTread extends Thread {
    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Additional thread: " + Thread.currentThread().getName());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(String.format("Thread '%s' was interrupted", Thread.currentThread().getName()));
                return;
            }
        }
    }
}

class Task implements Runnable {
    private String info;

    public Task(String info) {
        this.info = info;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println(info + " Additional thread: " + Thread.currentThread().getName());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(String.format("Thread '%s' was interrupted", Thread.currentThread().getName()));
                return;
            }
        }
    }
}