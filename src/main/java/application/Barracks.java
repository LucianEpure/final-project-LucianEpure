package application;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages = {"entity","application","repository","converter","config","controller","dto","service","validators"})
@EnableJpaRepositories(basePackages = {"repository"})
@PropertySource(value = "classpath:application.properties")
@EntityScan(basePackages ={"entity"})
public class Barracks {
        public static void main(String[] args) {
            SpringApplication.run(Barracks.class, args);
        }
}
