package ro.msg.learning.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;

@Configuration
public class MapperConfig {
	@Bean("modelMapper")
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean("csvMapper")
	public CsvMapper csvMapper() {
		return new CsvMapper();
	}
}
