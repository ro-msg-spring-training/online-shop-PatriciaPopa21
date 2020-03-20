package ro.msg.learning;

import java.io.FileNotFoundException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EntityScan(basePackages = { "ro.msg.learning.entity" }) // scan JPA entities
@EnableJpaRepositories(basePackages = "ro.msg.learning.repository") // scan JPA DAO's
@EnableScheduling
public class ShopApplication {

	public static void main(final String[] args) throws FileNotFoundException {
		SpringApplication.run(ShopApplication.class, args);
	}
}
