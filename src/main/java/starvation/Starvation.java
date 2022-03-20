package starvation;

import org.apache.log4j.Logger;

import java.util.stream.IntStream;

class Starvation {
    static Integer count = 1;
    static Logger logger = Logger.getLogger(Starvation.class);

    public static void run(int id) {
        IntStream.rangeClosed(1, 100).forEach(it -> {
            synchronized (count) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count++;
            }
        });
        logger.info("Thread execution completes" + " " + id);
    }

    public static void main(String[] args) throws InterruptedException {
        logger.info("Parent thread execution starts");

        /* Priority of each thread given. */
        /* Thread 1 with priority 7. */
        Thread thread1 = new Thread(() -> run(1));
        thread1.setPriority(10);
        /* Thread 2 with priority 6. */
        Thread thread2 = new Thread(() -> run(2));
        thread2.setPriority(8);
        /* Thread 3 with priority 5. */
        Thread thread3 = new Thread(() -> run(3));
        thread3.setPriority(6);
        /* Thread 4 with priority 4. */
        Thread thread4 = new Thread(() -> run(4));
        thread4.setPriority(4);
        /* Thread 5 with priority 3. */
        Thread thread5 = new Thread(() -> run(5));
        thread5.setPriority(1);

        thread5.start();
        thread4.start();
        thread2.start();
        thread3.start();
        thread1.start();

        logger.info("Parent thread execution completes");

        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();
        thread5.join();
    }
}
