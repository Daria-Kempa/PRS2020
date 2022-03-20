package volatilevariable;

import org.apache.log4j.Logger;

public class Volatile {

    static Logger logger = Logger.getLogger(Volatile.class);
    static volatile boolean terminated = false;

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            while (!terminated) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                logger.info("Processing");
            }
        });


        t1.start();

        Thread.sleep(3000);
        logger.info("Stop thread");
        terminated = true;

        t1.join();
        logger.info("Finished processing");
    }
}


