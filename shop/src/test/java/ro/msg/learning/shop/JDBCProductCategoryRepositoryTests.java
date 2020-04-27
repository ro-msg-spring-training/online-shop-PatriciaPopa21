package ro.msg.learning.shop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

import ro.msg.learning.entity.ProductCategory;
import ro.msg.learning.mapper.ProductCategoryMapper;
import ro.msg.learning.repository.JDBCProductCategoryRepository;

@DataJpaTest
@Import({ JDBCProductCategoryRepository.class, ProductCategoryMapper.class })
public class JDBCProductCategoryRepositoryTests {
	private final ProductCategory productCategory1 = new ProductCategory("Books", "Classical literature");
	private final ProductCategory productCategory2 = new ProductCategory("Watches", "Hand watch");

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private JDBCProductCategoryRepository jdbcProductCategoryRepository;

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
	}

	@Test
	void When_CategoryWithGivenIdExists_Expect_ReturnCategory() {
		final Integer id1 = (Integer) entityManager.getId(productCategory1);
		final Optional<ProductCategory> result = jdbcProductCategoryRepository.findById(id1);

		assertTrue(result.isPresent());

		final ProductCategory pc = result.get();

		assertEquals(productCategory1, pc);
	}

	@Test
	void Expect_ReturnAllCategories() {
		final List<ProductCategory> result = StreamSupport
				.stream(jdbcProductCategoryRepository.findAll().spliterator(), false).collect(Collectors.toList());

		assertTrue(result.size() == 2);
		assertEquals(productCategory1, result.get(0));
		assertEquals(productCategory2, result.get(1));
	}

	@Test
	void When_GivenNewCategory_Expect_CreateAndReturnCategory() {
		final ProductCategory pc = new ProductCategory("TVs", "Flat screen TVs");

		final ProductCategory result = jdbcProductCategoryRepository.save(pc);

		assertEquals(pc.getCategoryName(), result.getCategoryName());
		assertEquals(pc.getCategoryDescription(), result.getCategoryDescription());
		assertNotEquals(null, result.getId());
	}

	@Test
	void When_GivenExistingCategoryIdAndCategoryObject_Expect_UpdateAndReturnCategory() {
		final Integer id1 = (Integer) entityManager.getId(productCategory1);

		final ProductCategory pc = new ProductCategory("Books", "Science Fiction");
		pc.setId(id1);

		final ProductCategory result = jdbcProductCategoryRepository.save(pc);

		assertEquals(pc.getCategoryName(), result.getCategoryName());
		assertEquals(pc.getCategoryDescription(), result.getCategoryDescription());
		assertEquals(pc.getId(), result.getId());

		entityManager.refresh(productCategory1);

		/*
		 * check that the actual update has been performed (we haven't accidentally
		 * returned the input without doing anything with it)
		 */
		final ProductCategory updatedPc = entityManager.find(ProductCategory.class, id1);

		assertEquals(pc.getCategoryName(), updatedPc.getCategoryName());
		assertEquals(pc.getCategoryDescription(), updatedPc.getCategoryDescription());
		assertEquals(pc.getId(), updatedPc.getId());
	}

	@Test
	void When_GivenExistingCategoryId_Expect_RetrieveingTheCategoryToFailAfterDeletingThatCategory() {
		final Integer id1 = (Integer) entityManager.getId(productCategory1);

		jdbcProductCategoryRepository.deleteById(id1);

		Assertions.assertThrows(EntityNotFoundException.class, () -> {
			entityManager.refresh(productCategory1);
		});
	}
}
