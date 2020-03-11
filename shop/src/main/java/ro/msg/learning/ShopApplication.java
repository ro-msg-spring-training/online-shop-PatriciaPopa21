package ro.msg.learning;

import java.io.FileNotFoundException;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EntityScan(basePackages = {"ro.msg.learning.entity"})  // scan JPA entities
@EnableJpaRepositories(basePackages = "ro.msg.learning.repository") // scan JPA DAO's
public class ShopApplication {

	private static final Logger log = LoggerFactory.getLogger(ShopApplication.class);

	public static void main(final String[] args) throws FileNotFoundException {
		SpringApplication.run(ShopApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	//	@Bean
	//	public CommandLineRunner addSomeData(final SupplierRepository supplierRepository,
	//			final ProductCategoryRepository productCategoryRepository, final ProductRepository productRepository) {
	//		return (args) -> {
	//			final Supplier s1 = new Supplier("Emag");
	//			final Supplier s2 = new Supplier("Elefant.ro");
	//			supplierRepository.save(s1);
	//			supplierRepository.save(s2);
	//
	//			final ProductCategory pc1 = new ProductCategory("Books", "Classical literature");
	//			final ProductCategory pc2 = new ProductCategory("Watches", "Hand watch");
	//			productCategoryRepository.save(pc1);
	//			productCategoryRepository.save(pc2);
	//
	//			productRepository.save(new Product("Jane Eyre", "A nice book", new BigDecimal(10), 10.5, pc1, s2, "/janeEyre"));
	//			productRepository.save(new Product("Quo Vadis", "Another nice book", new BigDecimal(15), 13.2, pc1, s1, "/quoVadis"));
	//			productRepository.save(new Product("GShock X33", "A nice watch", new BigDecimal(200), 120.9, pc2, s1, "/gShockX33"));
	//		};
	//	}
}
