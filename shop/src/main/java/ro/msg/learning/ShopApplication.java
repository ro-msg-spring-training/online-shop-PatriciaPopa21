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

	private static final Logger LOG = LoggerFactory.getLogger(ShopApplication.class);

	public static void main(final String[] args) throws FileNotFoundException {
		SpringApplication.run(ShopApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	//	@Bean("csvMapper")
	//	public CsvMapper csvMapper() {
	//		return new CsvMapper();
	//	}
}
