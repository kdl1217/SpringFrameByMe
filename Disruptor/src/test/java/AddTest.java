import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * note
 *
 * @author Kong, created on 2018-08-23T17:58.
 * @since 1.2.0-SNAPSHOT
 */
public class AddTest {

    private AtomicInteger number = new AtomicInteger(0) ;

    public static void main(String[] args) {
        AddTest addTest = new AddTest() ;
        addTest.add();
        System.out.println(addTest.number.get());
    }


    private void add(){
        ExecutorService executorService = Executors.newFixedThreadPool(100) ;

        for (int i = 0; i < 100 ; i++) {
            executorService.submit(()->number.getAndAdd(1)) ;
        }
    }
}
