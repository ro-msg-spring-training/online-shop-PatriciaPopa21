package ro.msg.learning.service.interfaces;

import java.util.Optional;

import ro.msg.learning.entity.Supplier;

public interface SupplierService {
	Optional<Supplier> getSupplier(Integer id);
}
