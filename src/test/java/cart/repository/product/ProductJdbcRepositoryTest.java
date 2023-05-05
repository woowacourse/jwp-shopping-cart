package cart.repository.product;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.jdbc.Sql;

import cart.dao.cart.CartDao;
import cart.dao.product.ProductDao;
import cart.domain.product.Product;
import cart.domain.product.ProductRepository;
import cart.service.product.dto.SaveProductDto;
import cart.service.product.dto.UpdateProductDto;

@JdbcTest
@Sql(value = {"classpath:tearDown.sql", "classpath:setTest.sql"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductJdbcRepositoryTest {

	private ProductRepository productRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private final RowMapper<Product> productRowMapper = (rs, rowNum) -> new Product(
		rs.getLong("id"),
		rs.getString("name"),
		rs.getString("image"),
		rs.getInt("price"),
		rs.getTimestamp("created_at").toLocalDateTime(),
		rs.getTimestamp("updated_at").toLocalDateTime()
	);

	@BeforeEach
	public void setProduct() {
		productRepository = new ProductJdbcRepository(new ProductDao(jdbcTemplate), new CartDao(jdbcTemplate));
	}

	@Test
	void findAllTest() {
		// when
		final List<Product> result = productRepository.findAll();

		// then
		assertThat(result).isNotNull();
		assertThat(result).hasSize(3);

	}

	@Test
	void saveProductsTest() {
		// given
		final SaveProductDto saveProductDto = new SaveProductDto("name", "image", 10);

		// when
		final Product savedProduct = productRepository.saveProducts(new Product(saveProductDto));

		// then
		List<Product> products = jdbcTemplate.query("SELECT * FROM PRODUCT", productRowMapper);
		final Product result = products.get(products.size() - 1);

		assertThat(result.getName()).isEqualTo(savedProduct.getName());
		assertThat(result.getImage()).isEqualTo(savedProduct.getImage());
		assertThat(result.getPrice()).isEqualTo(savedProduct.getPrice());
	}

	@Test
	void findByIdTest() {
		// given
		final List<Product> products = jdbcTemplate.query("SELECT * FROM PRODUCT", productRowMapper);
		final Product product = products.get(products.size() - 1);
		final Long productId = product.getId();

		// when
		final Optional<Product> optionalProduct = productRepository.findById(productId);

		// then
		assertThat(optionalProduct).isPresent();
	}

	@Test
	void updateProductsTest() {
		// given
		final List<Product> products = jdbcTemplate.query("SELECT * FROM PRODUCT", productRowMapper);
		final Product product = products.get(0);
		final Long productId = product.getId();
		final Product updateProduct = new Product(new UpdateProductDto(productId, "name", "image", 10));

		// when
		productRepository.updateProducts(updateProduct);
		final Product result = jdbcTemplate.queryForObject("SELECT * FROM product where id = ?", productRowMapper,
			productId);

		// then
		assertThat(result.getId()).isEqualTo(updateProduct.getId());
		assertThat(result.getName()).isEqualTo(updateProduct.getName());
		assertThat(result.getImage()).isEqualTo(updateProduct.getImage());
		assertThat(result.getPrice()).isEqualTo(updateProduct.getPrice());
	}

	@Test
	void deleteProductsById() {
		// given
		final List<Product> products = jdbcTemplate.query("SELECT * FROM PRODUCT", productRowMapper);
		final Product product = products.get(products.size() - 1);
		final Long productId = product.getId();

		// when
		productRepository.deleteProductsById(productId);

		// then
		final Optional<Product> optionalProduct = productRepository.findById(productId);
		assertThat(optionalProduct).isNotPresent();
	}

}
