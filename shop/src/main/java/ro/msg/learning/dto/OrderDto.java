package ro.msg.learning.dto;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.msg.learning.entity.Address;
import ro.msg.learning.entity.Customer;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
	private LocalDateTime createdAt;
	private Customer customer;
	private Address address;
	// since JSON only allows String keys for maps, I have passed the product Id as String
	private Map<String, Integer> productsAndCorrespondingQuantities;
}