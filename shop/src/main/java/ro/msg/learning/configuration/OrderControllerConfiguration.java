package ro.msg.learning.configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import ro.msg.learning.service.OrderDetailService;

@Configuration
public class OrderControllerConfiguration {
	@Value("${strategy}")
	private String strategyName;

	private Map<String, OrderDetailService> availableStrategies;

	public OrderControllerConfiguration(final List<OrderDetailService> strategyImplementations) {
		availableStrategies = getStrategyImplementationsMap(strategyImplementations);
	}

	private Map<String, OrderDetailService> getStrategyImplementationsMap(final List<OrderDetailService> strategyImplementations) {
		final Map<String, OrderDetailService> availableStrategies = new HashMap<>();

		for (final OrderDetailService strategy : strategyImplementations) {
			availableStrategies.put(strategy.getName(), strategy);
		}

		return availableStrategies;
	}

	@Bean
	@Primary
	public OrderDetailService strategy() {
		return availableStrategies.get(strategyName);
	}
}
