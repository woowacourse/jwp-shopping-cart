package cart.dao.cart;

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

import cart.dao.cart.dto.CartProductDto;
import cart.domain.cart.Cart;

@JdbcTest
@Sql(value = {"classpath:tearDown.sql", "classpath:setTest.sql"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CartDaoTest {

	private CartDao cartDao;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private final RowMapper<Cart> cartRowMapper = (rs, rowNum) -> new Cart(
		rs.getLong("id"),
		rs.getLong("users_id"),
		rs.getLong("product_id"),
		rs.getInt("quantity")
	);

	@BeforeEach
	public void setTest() {
		cartDao = new CartDao(jdbcTemplate);
	}

	@Test
	void saveTest() {
		// given
		final long userId = 2L;
		final long productId = 2L;
		final Cart cart = new Cart(userId, productId);

		// when
		cartDao.save(cart);
		final List<Cart> carts = jdbcTemplate.query("SELECT * FROM cart", cartRowMapper);
		final Cart savedCart = carts.get(carts.size() - 1);

		// then
		assertThat(savedCart.getUserId()).isEqualTo(userId);
		assertThat(savedCart.getProductId()).isEqualTo(productId);
	}

	@Test
	void findProductByEmail() {
		// given
		final String email = "test@test.com";

		// when
		final List<CartProductDto> products = cartDao.findProductByEmail(email);

		// then
		assertThat(products).hasSize(1);
	}

	@Test
	void findByIdsTest() {
		//given
		final long userId = 1L;
		final long productId = 1L;

		//when
		final Optional<CartProductDto> cartProductDto = cartDao.findByIds(userId, productId);

		//then
		assertThat(cartProductDto).isPresent();
	}

	@Test
	void updateByCartIdTest() {
		//given
		final long userId = 1L;
		final long productId = 1L;
		final CartProductDto cartProductDto = cartDao.findByIds(userId, productId).get();
		final Long cartId = cartProductDto.getId();

		//when
		cartDao.updateQuantityByCartId(cartId);
		final CartProductDto result = cartDao.findByIds(userId, productId).get();

		//then
		assertThat(result.getQuantity()).isEqualTo(2);
	}

	@Test
	void deleteByCartIdTest() {
		//given
		final long userId = 1L;
		final long productId = 1L;
		final CartProductDto cartProductDto = cartDao.findByIds(userId, productId).get();
		final Long cartId = cartProductDto.getId();

		//when
		cartDao.deleteByCartId(cartId);
		final Optional<CartProductDto> result = cartDao.findByIds(userId, productId);

		//then
		assertThat(result).isNotPresent();
	}

	@Test
	void deleteByProductId() {
		// given
		final long productId = 1L;
		final List<Cart> carts = jdbcTemplate.query("SELECT * FROM cart WHERE product_id = ?", cartRowMapper,
			productId);

		// when
		cartDao.deleteByProductId(1L);

		// then
		final List<Cart> result = jdbcTemplate.query("SELECT * FROM cart WHERE product_id = ?", cartRowMapper,
			productId);

		assertThat(carts.size()).isNotEqualTo(0);
		assertThat(result).hasSize(0);
	}

}
