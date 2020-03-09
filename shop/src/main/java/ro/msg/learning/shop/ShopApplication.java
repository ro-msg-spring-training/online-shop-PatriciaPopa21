package ro.msg.learning.shop;

import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EntityScan(basePackages = {"ro.msg.learning.entity"})  // scan JPA entities
@EnableJpaRepositories(basePackages = "ro.msg.learning.repository") // scan JPA DAO's
public class ShopApplication {

	private static final Logger log = LoggerFactory.getLogger(ShopApplication.class);

	public static void main(final String[] args) throws FileNotFoundException {
		SpringApplication.run(ShopApplication.class, args);
	}
}
