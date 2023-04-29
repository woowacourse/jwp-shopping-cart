package cart.product.dao;

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

import cart.product.entity.Product;

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
		rs.getLong("price"),
		rs.getTimestamp("created_at").toLocalDateTime(),
		rs.getTimestamp("updated_at").toLocalDateTime()
	);

	@BeforeEach
	public void setProduct() {
		productDao = new ProductDao(jdbcTemplate);
		product = new Product(null, "name", "image", 10000L, null, null);
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
		assertThat(savedProduct.getName()).isEqualTo(this.product.getName());
		assertThat(savedProduct.getImage()).isEqualTo(this.product.getImage());
		assertThat(savedProduct.getPrice()).isEqualTo(this.product.getPrice());
	}

	@Test
	void updateByIdTest() {
		// given
		long id = productDao.save(product);

		// when
		Product newProduct = new Product(null, "newName", "newImage", 0L, null, null);
		productDao.updateById(id, newProduct);

		// then
		Product findProduct = jdbcTemplate.queryForObject("SELECT * FROM PRODUCT WHERE id = ?", productRowMapper, id);
		assertThat(findProduct.getName()).isEqualTo(newProduct.getName());
		assertThat(findProduct.getImage()).isEqualTo(newProduct.getImage());
		assertThat(findProduct.getPrice()).isEqualTo(newProduct.getPrice());
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
