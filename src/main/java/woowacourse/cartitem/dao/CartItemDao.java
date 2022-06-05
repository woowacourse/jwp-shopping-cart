package woowacourse.cartitem.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import woowacourse.cartitem.domain.CartItem;

@Repository
public class CartItemDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public CartItemDao(final DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("cart_item")
            .usingGeneratedKeyColumns("id");
    }

    public Long addCartItem(final CartItem cartItem) {
        final SqlParameterSource params = new MapSqlParameterSource()
            .addValue("customer_id", cartItem.getCustomerId())
            .addValue("product_id", cartItem.getProductId())
            .addValue("quantity", cartItem.getQuantity().getValue());

        return jdbcInsert.executeAndReturnKey(params).longValue();
    }

    // public List<Long> findProductIdsByCustomerId(final Long customerId) {
    //     final String sql = "SELECT product_id FROM cart_item WHERE customer_id = ?";
    //
    //     return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("product_id"), customerId);
    // }
    //
    // public List<Long> findIdsByCustomerId(final Long customerId) {
    //     final String sql = "SELECT id FROM cart_item WHERE customer_id = ?";
    //
    //     return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("id"), customerId);
    // }
    //
    // public Long findProductIdById(final Long cartId) {
    //     try {
    //         final String sql = "SELECT product_id FROM cart_item WHERE id = ?";
    //         return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getLong("product_id"), cartId);
    //     } catch (EmptyResultDataAccessException e) {
    //         throw new InvalidCartItemException();
    //     }
    // }
    //
    // public void deleteCartItem(final Long id) {
    //     final String sql = "DELETE FROM cart_item WHERE id = ?";
    //
    //     final int rowCount = jdbcTemplate.update(sql, id);
    //     if (rowCount == 0) {
    //         throw new InvalidCartItemException();
    //     }
    // }
}
