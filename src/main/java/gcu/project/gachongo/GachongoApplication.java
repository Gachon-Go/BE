package gcu.project.gachongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GachongoApplication {

    public static void main(String[] args) {
        SpringApplication.run(GachongoApplication.class, args);
    }

}
