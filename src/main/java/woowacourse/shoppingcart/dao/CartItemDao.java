package woowacourse.shoppingcart.dao;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import woowacourse.exception.InvalidCartItemException;
import woowacourse.shoppingcart.domain.CartItem;

@Repository
public class CartItemDao {

	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	private final NamedParameterJdbcTemplate namedJdcTemplate;
	private final ProductDao productDao;

	public CartItemDao(DataSource dataSource, ProductDao productDao) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.jdbcInsert = new SimpleJdbcInsert(dataSource)
			.withTableName("cart_item")
			.usingGeneratedKeyColumns("id");
		this.namedJdcTemplate = new NamedParameterJdbcTemplate(dataSource);
		this.productDao = productDao;
	}

	public CartItem save(Long customerId, CartItem cartItem) {
		long cartItemId = jdbcInsert.executeAndReturnKey(
				new BeanPropertySqlParameterSource(new CartItemDto(
					customerId,
					cartItem.getProductId(),
					cartItem.getQuantity())))
			.longValue();
		return cartItem.createWithId(cartItemId);
	}

	public List<CartItem> findByCustomerId(Long customerId) {
		String sql = "SELECT * FROM cart_item WHERE customer_id = :customerId";
		return namedJdcTemplate.query(sql,
			Map.of("customerId", customerId),
			getCartItemMapper()
		);
	}

	private RowMapper<CartItem> getCartItemMapper() {
		return (rs, rowNum) -> new CartItem(
			rs.getLong("id"),
			productDao.findById(rs.getLong("product_id")),
			rs.getInt("quantity")
		);
	}

	public Long findProductIdById(final Long cartId) {
		try {
			final String sql = "SELECT product_id FROM cart_item WHERE id = ?";
			return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getLong("product_id"), cartId);
		} catch (EmptyResultDataAccessException e) {
			throw new InvalidCartItemException();
		}
	}

	public void deleteCartItem(final Long id) {
		final String sql = "DELETE FROM cart_item WHERE id = ?";

		final int rowCount = jdbcTemplate.update(sql, id);
		if (rowCount == 0) {
			throw new InvalidCartItemException();
		}
	}
}
