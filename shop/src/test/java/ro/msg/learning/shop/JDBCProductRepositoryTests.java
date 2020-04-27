package ro.msg.learning.shop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import ro.msg.learning.entity.Product;
import ro.msg.learning.entity.ProductCategory;
import ro.msg.learning.entity.Supplier;
import ro.msg.learning.mapper.ProductCategoryMapper;
import ro.msg.learning.mapper.ProductMapper;
import ro.msg.learning.repository.JDBCProductCategoryRepository;
import ro.msg.learning.repository.JDBCProductRepository;

@DataJpaTest
@Import({ JDBCProductRepository.class, ProductMapper.class, JDBCProductCategoryRepository.class,
		ProductCategoryMapper.class })
public class JDBCProductRepositoryTests {
	private final ProductCategory productCategory1 = new ProductCategory("Books", "Classical literature");
	private final ProductCategory productCategory2 = new ProductCategory("Watches", "Hand watch");

	private final Supplier supplier1 = new Supplier("Elefant.ro");
	private final Supplier supplier2 = new Supplier("Emag");

	private final Product product1 = new Product("Jane Eyre", "A nice book", new BigDecimal(25), 40.0, productCategory1,
			supplier1, "/janeEyre");
	private final Product product2 = new Product("GShock X33", "A nice watch", new BigDecimal(200), 120.9,
			productCategory2, supplier2, "/gShockX33");

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private JDBCProductRepository jdbcProductRepository;

	@BeforeEach
	@Transactional
	void init() {
		/*
		 * refresh() is needed for each entity in order to avoid
		 * TransientPropertyValueException
		 */

		entityManager.persist(productCategory1);
		entityManager.persist(productCategory2);

		entityManager.refresh(productCategory1);
		entityManager.refresh(productCategory2);

		entityManager.persist(supplier1);
		entityManager.persist(supplier2);

		entityManager.refresh(supplier1);
		entityManager.refresh(supplier2);

		entityManager.persist(product1);
		entityManager.persist(product2);

		entityManager.refresh(product1);
		entityManager.refresh(product2);
	}

	@Test
	void When_CategoryForGivenIdExists_Expect_ReturnCategory() {
		final Integer id1 = (Integer) entityManager.getId(product1);
		final Optional<Product> result = jdbcProductRepository.findById(id1);

		assertTrue(result.isPresent());

		final Product product = result.get();

		assertEquals(product1, product);
	}

	@Test
	void Expect_ReturnAllCategories() {
		final List<Product> result = StreamSupport.stream(jdbcProductRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());

		assertTrue(result.size() == 2);
		assertEquals(product1, result.get(0));
		assertEquals(product2, result.get(1));
	}

	@Test
	void When_GivenNewCategory_Expect_CreateAndReturnCategory() {
		final Product product = new Product("The Alchemist", "An amazing book", new BigDecimal(25), 40.0,
				productCategory1, supplier1, "/theAlchemist");

		final Product result = jdbcProductRepository.save(product);

		assertEquals(product.getProductName(), result.getProductName());
		assertEquals(product.getProductDescription(), result.getProductDescription());
		assertEquals(product.getPrice(), result.getPrice());
		assertEquals(product.getWeight(), result.getWeight());
		assertEquals(product.getCategory(), result.getCategory());
		assertEquals(product.getSupplier(), result.getSupplier());
		assertEquals(product.getImageUrl(), result.getImageUrl());
		assertNotEquals(null, result.getId());
	}

	@Test
	void When_GivenExistingCategoryIdAndCategoryObject_Expect_UpdateAndReturnCategory() {
		final Integer id1 = (Integer) entityManager.getId(product1);

		final Product product = new Product("The Alchemist", "A legendary book", new BigDecimal(25), 40.0,
				productCategory1, supplier1, "/theAlchemistNewCover");
		product.setId(id1);

		final Product result = jdbcProductRepository.save(product);

		assertEquals(product.getProductName(), result.getProductName());
		assertEquals(product.getProductDescription(), result.getProductDescription());
		assertEquals(product.getPrice(), result.getPrice());
		assertEquals(product.getWeight(), result.getWeight());
		assertEquals(product.getCategory(), result.getCategory());
		assertEquals(product.getSupplier(), result.getSupplier());
		assertEquals(product.getImageUrl(), result.getImageUrl());
		assertEquals(product.getId(), result.getId());

		entityManager.refresh(product1);

		/*
		 * check that the actual update has been performed (we haven't accidentally
		 * returned the input without doing anything with it)
		 */
		final Product updatedProduct = entityManager.find(Product.class, id1);

		assertEquals("The Alchemist", updatedProduct.getProductName());
		assertEquals("A legendary book", updatedProduct.getProductDescription());
		assertEquals(new BigDecimal(25).setScale(2), updatedProduct.getPrice());
		assertEquals(40.0, updatedProduct.getWeight());
		assertEquals(productCategory1, updatedProduct.getCategory());
		assertEquals(supplier1, updatedProduct.getSupplier());
		assertEquals("/theAlchemistNewCover", updatedProduct.getImageUrl());
		assertEquals(id1, updatedProduct.getId());
	}

	@Test
	void When_GivenExistingCategoryId_Expect_RetrieveingTheCategoryToFailAfterDeletingThatCategory() {
		final Integer id1 = (Integer) entityManager.getId(product1);

		jdbcProductRepository.deleteById(id1);

		Assertions.assertThrows(EntityNotFoundException.class, () -> {
			entityManager.refresh(product1);
		});
	}
}
