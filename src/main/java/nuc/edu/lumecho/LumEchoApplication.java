package nuc.edu.lumecho;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LumEchoApplication {

    public static void main(String[] args) {
        SpringApplication.run(LumEchoApplication.class, args);
    }
}

