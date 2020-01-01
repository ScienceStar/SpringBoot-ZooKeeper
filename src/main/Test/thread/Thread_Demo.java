package thread;

import com.example.spring_boot.HelloWord;
import org.junit.Test;
import runnable.BuildPotel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Thread_Demo {

    public static boolean isStop = false;

    public transient String userName;

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
    public void cycleSelf(){
        int i=0;

        for(;;){
            System.out.println(i++);
        }
    }

    public boolean isStop(Boolean flag){
        return false==true?true:false;
    }

    @Test
    public void testCal(){
        int a=3,b=4,c=5;

        int result = this.cal(a=b+c,a+c,c=a>5?8:6);
        System.out.println(result);
    }

    public int cal(int a,int b,int c){
        int result = a+b/c;
        return result;
    }

    /**
     * 多线程测试
     */
    @Test
    public void executorTest(){
        ExecutorService executorService = Executors.newFixedThreadPool(5);

       for(int i=0;i<10;i++){
           executorService.execute(new BuildPotel());
       }
       executorService.shutdown();
    }

    @Test
    public void show(){
        HelloWord helloWord = new HelloWord();
        helloWord.show();
    }
}
