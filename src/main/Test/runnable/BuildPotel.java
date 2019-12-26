package runnable;

public class BuildPotel implements Runnable{
    int i=0;
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }
}
