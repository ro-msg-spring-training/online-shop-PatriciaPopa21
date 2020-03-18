package ro.msg.learning.service;

import java.util.Optional;

import ro.msg.learning.entity.Supplier;

public interface SupplierService {
	Optional<Supplier> getSupplier(Integer id);
}
