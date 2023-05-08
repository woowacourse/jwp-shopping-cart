package cart.service.cart;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import cart.dao.cart.dto.CartProductDto;
import cart.domain.cart.Cart;
import cart.domain.product.Product;
import cart.domain.user.User;
import cart.service.cart.dto.CartDto;

@Transactional
@SpringBootTest
@Sql(value = {"classpath:tearDown.sql", "classpath:setTest.sql"})
class CartServiceTest {

	@Autowired
	private CartService cartService;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private final RowMapper<User> userRowMapper = (rs, rowNum) -> new User(
		rs.getLong("id"),
		rs.getString("email"),
		rs.getString("password")
	);

	private final RowMapper<Product> productRowMapper = (rs, rowNum) -> new Product(
		rs.getLong("id"),
		rs.getString("name"),
		rs.getString("image"),
		rs.getInt("price"),
		rs.getTimestamp("created_at").toLocalDateTime(),
		rs.getTimestamp("updated_at").toLocalDateTime()
	);

	private final RowMapper<Cart> cartRowMapper = (resultSet, rowNum) -> new Cart(
		resultSet.getLong("id"),
		resultSet.getLong("users_id"),
		resultSet.getLong("product_id"),
		resultSet.getInt("quantity")
	);

	@Test
	void addProductTest() {
		//given
		final List<User> users = jdbcTemplate.query("SELECT * FROM USERS", userRowMapper);
		List<Product> products = jdbcTemplate.query("SELECT * FROM PRODUCT", productRowMapper);

		final Long userId = users.get(0).getId();
		final Long productId = products.get(products.size() - 1).getId();

		//when
		final CartDto cartDto = cartService.addProduct(userId, productId);

		//then
		final Cart cart = jdbcTemplate.queryForObject(
			"SELECT * FROM cart WHERE users_id = ? AND product_id = ?",
			cartRowMapper, userId, productId);

		assertThat(cartDto.getId()).isEqualTo(cart.getId());
		assertThat(cartDto.getUserId()).isEqualTo(cart.getUserId());
		assertThat(cartDto.getProductId()).isEqualTo(cart.getProductId());
		assertThat(cartDto.getQuantity()).isEqualTo(cart.getQuantity());
	}

	@Test
	void findByEmailTest() {
		//given
		final List<User> users = jdbcTemplate.query("SELECT * FROM USERS", userRowMapper);
		final String email = users.get(0).getEmail();
		final Long userId = users.get(0).getId();

		//when
		final List<CartProductDto> result = cartService.findByEmail(email);

		//then
		final List<Cart> carts = jdbcTemplate.query("SELECT * FROM cart WHERE users_id = ? ", cartRowMapper, userId);

		assertThat(result).hasSize(carts.size());
	}

	@Test
	void updateQuantityTest() {
		// given
		final List<User> users = jdbcTemplate.query("SELECT * FROM USERS", userRowMapper);
		List<Product> products = jdbcTemplate.query("SELECT * FROM PRODUCT", productRowMapper);

		final Long userId = users.get(0).getId();
		final Long productId = products.get(0).getId();
		final Cart cart = jdbcTemplate.queryForObject(
			"SELECT * FROM cart WHERE users_id = ? AND product_id = ?",
			cartRowMapper, userId, productId);
		final Integer prevQuantity = cart.getQuantity();

		// when
		final CartDto cartDto = cartService.updateQuantity(userId, productId);

		// then
		final Cart updatedCart = jdbcTemplate.queryForObject(
			"SELECT * FROM cart WHERE users_id = ? AND product_id = ?",
			cartRowMapper, userId, productId);

		assertThat(updatedCart.getQuantity()).isEqualTo(prevQuantity + 1);
		assertThat(updatedCart.getQuantity()).isEqualTo(cartDto.getQuantity());
	}

	@Test
	void deleteProductTest() {
		// given
		final List<User> users = jdbcTemplate.query("SELECT * FROM USERS", userRowMapper);
		List<Product> products = jdbcTemplate.query("SELECT * FROM PRODUCT", productRowMapper);

		final Long userId = users.get(0).getId();
		final Long productId = products.get(0).getId();

		// when
		cartService.deleteProduct(userId, productId);

		// then
		assertThatThrownBy(() -> jdbcTemplate.queryForObject(
			"SELECT * FROM cart WHERE users_id = ? AND product_id = ?",
			cartRowMapper, userId, productId))
			.isInstanceOf(EmptyResultDataAccessException.class);

	}
}
