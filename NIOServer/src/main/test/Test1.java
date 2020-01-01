import had.ThreadHandller;
import org.junit.Test;

public class Test1 {

    @Test
    public void show(){
        System.out.println("hello");
    }

    @Test
    public void testThread(){
        ThreadHandller threadHandller = new ThreadHandller();
        threadHandller.show();
    }

    @Test
    public void test3(){
        System.out.println("lll");
    }
}
