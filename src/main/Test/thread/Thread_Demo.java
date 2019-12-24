package thread;


import org.junit.Test;

public class Thread_Demo {

    public static boolean isStop = false;

    @Test
    public void testThread() throws InterruptedException {

        Thread t1 = new Thread(()->{
            int i = 0;
            System.out.println(Thread.currentThread().getName()+"is stated!");

            while (i<10){
                System.out.println(i++);
            }
        });

        t1.start();
        t1.sleep(1000);

    }
}
