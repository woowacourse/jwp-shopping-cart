package cart.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import cart.domain.cart.Cart;
import cart.domain.cart.CartId;
import cart.domain.member.MemberId;
import cart.domain.product.ProductId;

@Repository
public class CartRepository {
	private static final int DELETED_COUNT = 1;
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;

	private final RowMapper<Cart> cartRowMapper =
		(resultSet, rowNum) ->
			new Cart(
				CartId.from(resultSet.getLong("id")),
				MemberId.from(resultSet.getLong("memberId")),
				ProductId.from(resultSet.getLong("productId"))
			);

	public CartRepository(final JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
			.withTableName("carts")
			.usingGeneratedKeyColumns("id");
	}

	public CartId insert(final MemberId memberId, final ProductId productId) {
		SqlParameterSource params = new MapSqlParameterSource()
			.addValue("memberId", memberId.getValue())
			.addValue("productId", productId.getId());
		final long cartId = jdbcInsert.executeAndReturnKey(params).longValue();
		return CartId.from(cartId);
	}

	public List<Cart> findAllByMemberId(final MemberId memberId) {
		final String sql = "SELECT * FROM carts WHERE memberId = ?";
		return jdbcTemplate.query(sql, cartRowMapper, memberId.getValue());
	}

	public List<Cart> findAllByEmail(final String email) {
		final String sql =
			"SELECT * "
				+ "FROM members m "
				+ "JOIN carts c ON m.id = c.memberId "
				+ "JOIN products p ON c.productId = p.id "
				+ "WHERE m.email = ?";
		return jdbcTemplate.query(sql, cartRowMapper, email);
	}

	public boolean deleteById(final MemberId memberId, final long cartId) {
		final String sql = "DELETE FROM carts WHERE id = ? AND memberId = ?";
		final int deleteCount = jdbcTemplate.update(sql, cartId, memberId.getValue());

		return deleteCount == DELETED_COUNT;
	}
}
