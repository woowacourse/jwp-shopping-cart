package woowacourse.shoppingcart.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import woowacourse.exception.InvalidCartItemException;
import woowacourse.shoppingcart.domain.CartItem;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

@Repository
public class CartItemDao {

	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;

	public CartItemDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.jdbcInsert = new SimpleJdbcInsert(dataSource)
			.withTableName("cart_item")
			.usingGeneratedKeyColumns("id");
	}

	public List<Long> findProductIdsByCustomerId(final Long customerId) {
		final String sql = "SELECT product_id FROM cart_item WHERE customer_id = ?";

		return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("product_id"), customerId);
	}

	public List<Long> findIdsByCustomerId(final Long customerId) {
		final String sql = "SELECT id FROM cart_item WHERE customer_id = ?";

		return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("id"), customerId);
	}

	public Long findProductIdById(final Long cartId) {
		try {
			final String sql = "SELECT product_id FROM cart_item WHERE id = ?";
			return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getLong("product_id"), cartId);
		} catch (EmptyResultDataAccessException e) {
			throw new InvalidCartItemException();
		}
	}

	public Long addCartItem(final Long customerId, final Long productId) {
		final String sql = "INSERT INTO cart_item(customer_id, product_id) VALUES(?, ?)";
		final KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(con -> {
			PreparedStatement preparedStatement = con.prepareStatement(sql, new String[] {"id"});
			preparedStatement.setLong(1, customerId);
			preparedStatement.setLong(2, productId);
			return preparedStatement;
		}, keyHolder);
		return keyHolder.getKey().longValue();
	}

	public CartItem save(Long customerId, CartItem cartItem) {
		long cartItemId = jdbcInsert.executeAndReturnKey(Map.of(
				"customer_id", customerId,
				"product_id", cartItem.getProductId(),
				"quantity", cartItem.getQuantity()))
			.longValue();
		return cartItem.createWithId(cartItemId);
	}

	public void deleteCartItem(final Long id) {
		final String sql = "DELETE FROM cart_item WHERE id = ?";

		final int rowCount = jdbcTemplate.update(sql, id);
		if (rowCount == 0) {
			throw new InvalidCartItemException();
		}
	}
}
