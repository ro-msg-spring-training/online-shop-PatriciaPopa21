package ro.msg.learning.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ro.msg.learning.entity.Supplier;
import ro.msg.learning.repository.SupplierRepository;
import ro.msg.learning.service.interfaces.SupplierService;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {
	private final SupplierRepository supplierRepository;

	@Override
	public Optional<Supplier> getSupplier(final Integer id) {
		return supplierRepository.findById(id);
	}

}
