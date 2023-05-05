package cart.dao.cart;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cart.dao.cart.dto.CartProductDto;
import cart.domain.cart.Cart;

@Component
public class CartDao {

	private static final int INIT_QUANTITY = 1;

	private final JdbcTemplate jdbcTemplate;
	private final RowMapper<CartProductDto> cartProductDtoRowMapper = (resultSet, rowNum) -> new CartProductDto(
		resultSet.getLong("cart.id"),
		resultSet.getString("product.name"),
		resultSet.getString("product.image"),
		resultSet.getInt("product.price"),
		resultSet.getInt("cart.quantity")
	);

	public CartDao(final JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Transactional
	public Cart save(final Cart cart) {
		final String sql = "INSERT INTO cart (users_id, product_id) VALUES (?, ?)";

		final KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
			ps.setLong(1, cart.getUserId());
			ps.setLong(2, cart.getProductId());
			return ps;
		}, keyHolder);

		final long id = (long)Objects.requireNonNull(keyHolder.getKeys()).get("id");
		cart.setId(id);
		cart.setQuantity(INIT_QUANTITY);

		return cart;
	}

	@Transactional
	public List<CartProductDto> findProductByEmail(final String email) {
		final String sql = "SELECT cart.id, product.name, product.image, product.price, cart.quantity "
			+ "FROM cart "
			+ "LEFT JOIN users ON cart.users_id = users.id "
			+ "LEFT JOIN product ON cart.product_id = product.id "
			+ "where users.email = ?";

		return jdbcTemplate.query(sql, cartProductDtoRowMapper, email);
	}

	@Transactional
	public Optional<CartProductDto> findByIds(final Long userId, final Long productId) {
		final String sql = "SELECT cart.id, product.name, product.image, product.price, cart.quantity "
			+ "FROM cart "
			+ "LEFT JOIN users ON cart.users_id = users.id "
			+ "LEFT JOIN product ON cart.product_id = product.id "
			+ "WHERE cart.users_id = ? AND cart.product_id = ?";

		try {
			final CartProductDto cartProductDto = jdbcTemplate.queryForObject(sql, cartProductDtoRowMapper, userId,
				productId);
			return Optional.ofNullable(cartProductDto);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	public void updateByCartId(final Long cartId) {
		final String sql = "UPDATE cart SET quantity = quantity + 1 where id = ?";
		jdbcTemplate.update(sql, cartId);
	}

	public void deleteByCartId(final Long cartId) {
		final String sql = "DELETE FROM cart where id = ?";

		jdbcTemplate.update(sql, cartId);
	}

	public void deleteByProductId(final Long productId) {
		final String sql = "DELETE FROM cart where product_id = ?";
		jdbcTemplate.update(sql, productId);
	}
}
