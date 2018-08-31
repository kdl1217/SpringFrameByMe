package kdl.mq;

import kdl.mq.simulation.PerformanceCalc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot Launcher.
 *
 * @author Kong, created on 2018-08-02T17:26.
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
