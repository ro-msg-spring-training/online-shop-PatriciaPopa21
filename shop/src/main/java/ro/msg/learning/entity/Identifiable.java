package ro.msg.learning.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Data;

@MappedSuperclass
@Data
public abstract class Identifiable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;
}
