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
			.addValue("memberId", memberId.getId())
			.addValue("productId", productId.getId());
		final long cartId = jdbcInsert.executeAndReturnKey(params).longValue();
		return CartId.from(cartId);
	}

	public List<Cart> findAllByMemberId(final MemberId memberId) {
		final String sql = "SELECT * FROM carts WHERE memberId = ?";
		return jdbcTemplate.query(sql, cartRowMapper, memberId.getId());
	}

	public boolean deleteByMemberId(final MemberId memberId, final ProductId productId) {
		final String sql = "DELETE FROM carts WHERE memberId = ? AND productId = ?";
		final int deleteCount = jdbcTemplate.update(sql, memberId.getId(), productId.getId());

		return deleteCount == DELETED_COUNT;
	}
}
