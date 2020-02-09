package thread;

import org.json.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * @ClassName MutipleThread
 * @Description: TODO  多线程测试
 * @Author lxc
 * @Date 2020/2/9
 * @Version V1.0
 **/
public class MutipleThread {
    Logger log = LoggerFactory.getLogger(MutipleThread.class);

    @Test
    public void extendThread() {
        ThreadRuning threadRuning = new ThreadRuning("继承实现线程");
        threadRuning.start();
    }

    @Test
    public void runnableThread() {
        RunnableTest runnableTest1 = new RunnableTest();
        RunnableTest runnableTest2 = new RunnableTest();

        new Thread(runnableTest1, "Thread-1").start();
        new Thread(runnableTest2, "Thread-2").start();
    }

    @Test
    public void mutipleThreadTest() throws ExecutionException, InterruptedException {
        System.out.println("----程序开始运行----");
        Date date1 = new Date();

        int taskSize = 5;
        // 创建一个线程池
        ExecutorService pool = Executors.newFixedThreadPool(taskSize);
        // 创建多个有返回值的任务
        List<Future> list = new ArrayList<Future>();
        for (int i = 0; i < taskSize; i++) {
            Callable c = new RunnableTest.MyCallable(i + " ");
            // 执行任务并获取 Future 对象
            Future f = pool.submit(c);
            // System.out.println(">>>" + f.get().toString());
            list.add(f);
        }
        // 关闭线程池
        pool.shutdown();

        // 获取所有并发任务的运行结果
        for (Future f : list) {
            // 从 Future 对象上获取任务的返回值，并输出到控制台
            System.out.println(">>>" + f.get().toString());
        }

        Date date2 = new Date();
        System.out.println("----程序结束运行----，程序运行时间【"
                + (date2.getTime() - date1.getTime()) + "毫秒】");
    }

    @Test
    public void executorServiceTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        List<RunnableTest.MyCallable> callables = new ArrayList<>();
        for (int i = 0, len = 10; i < len; i++) {
            RunnableTest.MyCallable callable = new RunnableTest.MyCallable("task"+i);
            callables.add(callable);
        }
        List<Future<JSONObject>> resultList = new ArrayList<Future<JSONObject>>();
        try {
            resultList = executorService.invokeAll(callables);
        } catch (InterruptedException e) {
            log.error("execute concurrent thread error", e);
        } finally {
            if (!executorService.isShutdown() || !executorService.isTerminated()) {
                executorService.shutdown();
            }
        }

/**
 *批量获取线程执行结果，循环处理每条结果数据
 */
        for (Future<JSONObject> future : resultList) {
            JSONObject resp2 = null;
            try {
                resp2 = future.get();
            } catch (Exception e) {
                log.error("execute concurrent thread error", e);
            }
            if (resp2 == null) {
                continue;
            }
        }
    }
}

/**
 * @MethodName:
 * @Description: TODO 继承实现线程
 * @Param:
 * @Return:
 * @Author: lxc
 * @Date: 2020/2/9 14:32
 **/
class ThreadRuning extends Thread {

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public ThreadRuning(String name) {
        super(name);
    }

    @Override
    public void run() {

        while (true) {
            System.out.println(df.format(System.currentTimeMillis()));
            System.out.println(this);
        }
    }
}

/**
 * @MethodName:
 * @Description: TODO 实现Runnable 实现接口
 * @Param:
 * @Return:
 * @Author: lxc
 * @Date: 2020/2/9 14:43
 **/
class RunnableTest implements Runnable {

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void run() {
        while (true) {
            System.out.println(Thread.currentThread().getName() + "---->" + df.format(System.currentTimeMillis()));
        }
    }

    static class MyCallable implements Callable<JSONObject> {
        private String taskNum;

        MyCallable(String taskNum) {
            this.taskNum = taskNum;
        }

        public JSONObject call() throws Exception {
            System.out.println(">>>" + taskNum + "任务启动");
            Date dateTmp1 = new Date();
            Thread.sleep(1000);
            Date dateTmp2 = new Date();
            long time = dateTmp2.getTime() - dateTmp1.getTime();
            System.out.println(">>>" + taskNum + "任务终止");
            return null;
        }
    }
}
