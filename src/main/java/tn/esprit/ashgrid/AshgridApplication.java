package tn.esprit.ashgrid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class AshgridApplication {

    public static void main(String[] args) {
        SpringApplication.run(AshgridApplication.class, args);
    }

}
