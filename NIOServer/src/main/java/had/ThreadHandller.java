package had;

public class ThreadHandller extends Thread{
    public void show(){
        System.out.println("hello");
    }

    @Override
    public void run() {
        for(int i=0;i<10;i++){
            System.out.println(i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //super.run();
    }
}
