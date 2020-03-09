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

	// TODO remove this
	//	@Bean
	//	public CommandLineRunner demo(final SupplierRepository repository) {
	//		return (args) -> {
	//			// save a few suppliers
	//			repository.save(new Supplier("Jack"));
	//			repository.save(new Supplier("Chloe"));
	//			repository.save(new Supplier("Kim"));
	//			repository.save(new Supplier("David"));
	//			repository.save(new Supplier("Michelle"));
	//
	//			// fetch all suppliers
	//			log.info("Suppliers found with findAll():");
	//			log.info("-------------------------------");
	//			for (final Supplier customer : repository.findAll()) {
	//				log.info(customer.toString());
	//			}
	//			log.info("");
	//
	//			// fetch an individual supplier by ID
	//			final Supplier customer = repository.findById(1).get();
	//			log.info("Supplier found with findById(1L):");
	//			log.info("--------------------------------");
	//			log.info(customer.toString());
	//			log.info("");
	//
	//		};
	//	}
}
