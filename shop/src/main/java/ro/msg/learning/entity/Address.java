package ro.msg.learning.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@AttributeOverrides({
	@AttributeOverride( name = "country", column = @Column(name = "country")),
	@AttributeOverride( name = "city", column = @Column(name = "city")),
	@AttributeOverride( name = "county", column = @Column(name = "county")),
	@AttributeOverride( name = "streetAddress", column = @Column(name = "street_address"))
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({ "country", "city", "county", "streetAddress"})
public class Address {
	private String country;
	private String city;
	private String county;
	private String streetAddress;
}
