public class ThreadABC {
    private final Object monitor = new Object();
    private static volatile String currentOperation = "A";

    public void sendData() {
        try {
            synchronized (monitor) {
                while (!currentOperation.equals("B")) {
                    monitor.wait();
                }
                System.out.print("B");
                currentOperation = "C";
                monitor.notifyAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void prepareData() {
        synchronized (monitor) {
            try {
                while (!currentOperation.equals("A")) {
                    monitor.wait();
                }
                System.out.print("A");
                currentOperation = "B";
                monitor.notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void processData() {
        synchronized (monitor) {
            try {
                while (!currentOperation.equals("C")) {
                    monitor.wait();
                }
                System.out.print("C");
                currentOperation = "A";
                monitor.notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ThreadABC abc = new ThreadABC();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    abc.prepareData();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    abc.sendData();
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    abc.processData();
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();
    }
}
