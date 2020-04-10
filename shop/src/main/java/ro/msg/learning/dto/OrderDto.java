package ro.msg.learning.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import ro.msg.learning.entity.Address;
import ro.msg.learning.entity.OrderDetailDto;

@Data
@AllArgsConstructor
public class OrderDto {
	private LocalDateTime createdAt;
	private Integer customerId;
	private Address address;
	private List<OrderDetailDto> orderDetailDtos;
	
	public OrderDto() {
		orderDetailDtos = new ArrayList<OrderDetailDto>();
	}
}
