package com.kong;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBoot Launcher
 *
 * @author Kong, created on 2018-08-29T16:22.
 * @since 1.2.0-SNAPSHOT
 */
@SpringBootApplication
public class App implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(App.class,args) ;
    }


    @Override
    public void run(String... args) throws Exception {
        new Boot().init().start();
    }

}
