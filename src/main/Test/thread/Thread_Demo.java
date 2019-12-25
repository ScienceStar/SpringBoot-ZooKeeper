package thread;


import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class Thread_Demo {

    public static boolean isStop = false;

    @Test
    public void testThread() throws InterruptedException {

        AtomicInteger i= new AtomicInteger();
        Thread t1 = new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"is stated!");

            while (!isStop){
                System.out.println(i.getAndIncrement());
            }
        });

        i.set(2);
        t1.start();
        t1.sleep(1000);
        isStop=true;
    }
}
