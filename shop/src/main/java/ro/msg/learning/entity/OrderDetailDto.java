package ro.msg.learning.entity;

import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDto {
	private int productId;
	@Size(min=1)
	private int quantity;
	private String productName;
	
	
	/* productName is an optional argument, used only for the UI-version of the Create Order feature */
	public OrderDetailDto(int productId, int quantity) {
		super();
		this.productId = productId;
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return productName + " | " + quantity + "\n";
	}
}
