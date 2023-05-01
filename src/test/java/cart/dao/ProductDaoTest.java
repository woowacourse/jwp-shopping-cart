package cart.dao;

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

import cart.dao.product.ProductDao;
import cart.dao.product.dto.ProductCreateDTO;
import cart.dao.product.dto.ProductUpdateDTO;
import cart.domain.product.Product;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductDaoTest {

	private ProductDao productDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private ProductCreateDTO productCreateDTO;

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
		productCreateDTO = new ProductCreateDTO("name", "image", 10000);
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
		productDao.save(productCreateDTO);
		List<Product> products = jdbcTemplate.query("SELECT * FROM PRODUCT", productRowMapper);
		Product savedProduct = products.get(0);

		// then
		assertThat(savedProduct.getName()).isEqualTo(productCreateDTO.getName());
		assertThat(savedProduct.getImage()).isEqualTo(productCreateDTO.getImage());
		assertThat(savedProduct.getPrice()).isEqualTo(productCreateDTO.getPrice());
	}

	@Test
	void updateByIdTest() {
		// given
		long id = productDao.save(productCreateDTO);

		// when
		final ProductUpdateDTO productUpdateDTO = new ProductUpdateDTO(id, "newName", "newImage", 0);
		productDao.updateById(productUpdateDTO);

		// then
		Product findProduct = jdbcTemplate.queryForObject("SELECT * FROM PRODUCT WHERE id = ?", productRowMapper, id);
		assertThat(findProduct.getName()).isEqualTo(productUpdateDTO.getName());
		assertThat(findProduct.getImage()).isEqualTo(productUpdateDTO.getImage());
		assertThat(findProduct.getPrice()).isEqualTo(productUpdateDTO.getPrice());
	}

	@Test
	void deleteById() {
		//given
		long id = productDao.save(productCreateDTO);

		//when
		productDao.deleteById(id);

		//then
		assertThatThrownBy(
			() -> jdbcTemplate.queryForObject("SELECT * FROM PRODUCT WHERE id = ?", productRowMapper, id))
			.isInstanceOf(EmptyResultDataAccessException.class);

	}
}
