package thread;


import org.junit.Test;
import runnable.BuildPotel;

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

            if(isStop(false)){
                return;
            }
        });

        i.set(2);
        t1.start();
        t1.sleep(1000);
        isStop=true;
    }

    @Test
    public void runableThread() throws InterruptedException {
        Thread t1 = new Thread(new BuildPotel(),"Thread1");
        Thread t2 = new Thread(new BuildPotel(),"Thread2");
        t1.start();
        t1.sleep(1000);
        t2.start();
    }

    @Test
    public void cycleSelef(){
        int i=0;

        for(;;){
            System.out.println(i++);
        }
    }

    public boolean isStop(Boolean flag){
        return false==true?true:false;
    }
}
