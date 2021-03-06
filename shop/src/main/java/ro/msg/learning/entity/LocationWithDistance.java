package ro.msg.learning.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationWithDistance {
	private Location location;
	private double distanceFromTargetLocation;
}
