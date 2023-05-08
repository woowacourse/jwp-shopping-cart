package cart.dao.product;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import cart.domain.product.Product;
import cart.service.product.dto.SaveProductDto;
import cart.service.product.dto.UpdateProductDto;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductDaoTest {

	private ProductDao productDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private Product product;

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
		productDao = new ProductDao(jdbcTemplate);
		final SaveProductDto saveProductDto = new SaveProductDto("name", "image", 10000);
		product = new Product(saveProductDto);
	}

	@Test
	void findAllTest() {
		// when
		List<Product> all = productDao.findAll();

		// then
		assertThat(all).isNotNull();
		assertThat(all).hasSize(0);
	}

	@Test
	void saveTest() {
		// when
		productDao.save(product);
		List<Product> products = jdbcTemplate.query("SELECT * FROM PRODUCT", productRowMapper);
		Product savedProduct = products.get(0);

		// then
		assertThat(savedProduct.getName()).isEqualTo(product.getName());
		assertThat(savedProduct.getImage()).isEqualTo(product.getImage());
		assertThat(savedProduct.getPrice()).isEqualTo(product.getPrice());
	}

	@Test
	void updateByIdTest() {
		// given
		long id = productDao.save(product);

		// when
		final UpdateProductDto updateProductDto = new UpdateProductDto(id, "newName", "newImage", 0);
		final Product updatedProduct = new Product(updateProductDto);
		productDao.updateById(updatedProduct);

		// then
		Product findProduct = jdbcTemplate.queryForObject("SELECT * FROM PRODUCT WHERE id = ?", productRowMapper, id);
		assertThat(findProduct.getName()).isEqualTo(updatedProduct.getName());
		assertThat(findProduct.getImage()).isEqualTo(updatedProduct.getImage());
		assertThat(findProduct.getPrice()).isEqualTo(updatedProduct.getPrice());
	}

	@Test
	void deleteById() {
		//given
		long id = productDao.save(product);

		//when
		productDao.deleteById(id);

		//then
		assertThatThrownBy(
			() -> jdbcTemplate.queryForObject("SELECT * FROM PRODUCT WHERE id = ?", productRowMapper, id))
			.isInstanceOf(EmptyResultDataAccessException.class);

	}
}
