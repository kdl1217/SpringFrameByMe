package kdl.disruptor;

import kdl.disruptor.simulation.PerformanceCalc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBoot
 *
 * @author Kong, created on 2018-08-14T14:13.
 * @since 1.2.0-SNAPSHOT
 */
@SpringBootApplication
public class App implements CommandLineRunner {

    @Autowired
    private PerformanceCalc performanceCalc ;

    public static void main(String[] args) {
        SpringApplication.run(App.class,args) ;
    }

    @Override
    public void run(String... args) throws Exception {
        performanceCalc.start();
    }
}
