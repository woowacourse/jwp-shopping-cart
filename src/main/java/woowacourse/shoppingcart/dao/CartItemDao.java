package woowacourse.shoppingcart.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

@Repository
public class CartItemDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertActor;

    public CartItemDao(DataSource dataSource, NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertActor = new SimpleJdbcInsert(dataSource)
                .withTableName("cart_item")
                .usingGeneratedKeyColumns("id");
    }

    public Long addCartItem(final Long customerId, final Long productId, final Long count) {
        return insertActor.executeAndReturnKey(new MapSqlParameterSource()
                .addValue("customer_id", customerId)
                .addValue("product_id", productId)
                .addValue("count", count)
        ).longValue();
    }

    public List<Long> findProductIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT product_id FROM cart_item WHERE customer_id = :customerId";
        Map<String, Long> params = Map.of("customerId", customerId);

        return jdbcTemplate.query(sql, params, (rs, rowNum) -> rs.getLong("product_id"));
    }

    public List<Long> findIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT id FROM cart_item WHERE customer_id = :customerId";
        final Map<String, Long> params = Map.of("customerId", customerId);

        return jdbcTemplate.query(sql, params, (rs, rowNum) -> rs.getLong("id"));
    }

    public Long findProductIdById(final Long cartId) {
        try {
            final String sql = "SELECT product_id FROM cart_item WHERE id = :cartId";
            final Map<String, Long> params = Map.of("cartId", cartId);

            return jdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> rs.getLong("product_id"));
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public void deleteCartItemById(final Long cartId) {
        final String query = "DELETE FROM cart_item WHERE id = :cartId";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource("cartId", cartId);

        int rowCount = jdbcTemplate.update(query, namedParameters);

        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }
}
