package cart.repository.cart;

import cart.domain.cart.Cart;
import cart.domain.cart.CartId;
import cart.domain.member.MemberId;
import cart.domain.product.ProductId;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CartJdbcRepository implements CartRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CartJdbcRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("carts")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Cart> cartRowMapper = (rs, rowNum) -> {
        return new Cart(
                CartId.from(rs.getLong("id")),
                MemberId.from(rs.getLong("member_id")),
                ProductId.from(rs.getLong("product_id"))
        );
    };

    @Override
    public CartId saveByMemberId(final MemberId memberId, final ProductId productId) {
        final long cartId = simpleJdbcInsert.executeAndReturnKey(cartProductParamSource(productId, memberId)).longValue();
        return CartId.from(cartId);
    }

    private SqlParameterSource cartProductParamSource(final ProductId productId, final MemberId memberId) {
        return new MapSqlParameterSource()
                .addValue("member_id", memberId.getId())
                .addValue("product_id", productId.getId());
    }

    @Override
    public Optional<Cart> findByCartId(final CartId cartId) {
        final String sql = "SELECT * FROM carts WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, cartRowMapper, cartId.getId()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Cart> findAllByMemberId(final MemberId memberId) {
        final String sql = "SELECT * FROM carts WHERE member_id = ?";
        return jdbcTemplate.query(sql, cartRowMapper, memberId.getId());
    }

    @Override
    public int deleteByMemberId(final MemberId memberId, final ProductId productId) {
        final String sql = "DELETE FROM carts WHERE member_id = ? AND product_id = ?";
        return jdbcTemplate.update(sql, memberId.getId(), productId.getId());
    }
}
