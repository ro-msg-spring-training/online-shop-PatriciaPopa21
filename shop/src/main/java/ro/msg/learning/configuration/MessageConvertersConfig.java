package ro.msg.learning.configuration;

import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;
import ro.msg.learning.messageconverter.CsvMessageConverter;

@ComponentScan
@RequiredArgsConstructor
@EnableWebMvc
@Configuration
public class MessageConvertersConfig implements WebMvcConfigurer {

	private final CsvMessageConverter csvMessageConverter;

	@Override
	public void extendMessageConverters(final List<HttpMessageConverter<?>> converters) {
		converters.add(0, csvMessageConverter);
	}
}
