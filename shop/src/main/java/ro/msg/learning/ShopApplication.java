package ro.msg.learning;

import java.io.FileNotFoundException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
//@EnableAuthorizationServer
//@EnableResourceServer
//@EntityScan(basePackages = { "ro.msg.learning.entity" }) // scan JPA entities
//@EnableJpaRepositories(basePackages = "ro.msg.learning.repository") // scan JPA DAO's
public class ShopApplication {

	public static void main(final String[] args) throws FileNotFoundException {
		SpringApplication.run(ShopApplication.class, args);
	}
}
