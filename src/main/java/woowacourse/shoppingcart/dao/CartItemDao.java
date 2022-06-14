package woowacourse.shoppingcart.dao;

import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

@Repository
public class CartItemDao {

    private final NamedParameterJdbcTemplate namedJdbcTemplate;
    private final SimpleJdbcInsert simpleInsert;

    public CartItemDao(final DataSource dataSource) {
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.simpleInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("cart_item")
                .usingGeneratedKeyColumns("id");
    }

    public List<Long> findProductIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT product_id FROM cart_item WHERE customer_id = :customerId";
        final SqlParameterSource parameter = new MapSqlParameterSource(Map.of("customerId", customerId));
        return namedJdbcTemplate.queryForList(sql, parameter, Long.class);
    }

    public List<Long> findIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT id FROM cart_item WHERE customer_id = :customerId";
        final SqlParameterSource parameter = new MapSqlParameterSource(Map.of("customerId", customerId));
        return namedJdbcTemplate.queryForList(sql, parameter, Long.class);
    }

    public Long findProductIdById(final Long cartId) {
        try {
            final String sql = "SELECT product_id FROM cart_item WHERE id = :cartId";
            final SqlParameterSource parameter = new MapSqlParameterSource(Map.of("cartId", cartId));
            return namedJdbcTemplate.queryForObject(sql, parameter, Long.class);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public int findQuantityById(final Long cartId) {
        final String sql = "SELECT quantity FROM cart_item WHERE id=:cartId";
        final SqlParameterSource params = new MapSqlParameterSource(Map.of("cartId", cartId));
        return namedJdbcTemplate.queryForObject(sql, params, Integer.class);
    }

    public Long addCartItem(final Long customerId, final Long productId, final int quantity) {
        final Map<String, Object> params = Map.ofEntries(
                Map.entry("customer_id", customerId),
                Map.entry("product_id", productId),
                Map.entry("quantity", quantity)
        );
        return simpleInsert.executeAndReturnKey(params).longValue();
    }

    public boolean hasCartItem(Long productId, Long customerId) {
        final String sql = "SELECT EXISTS(SELECT * FROM cart_item WHERE product_id = :productId and customer_id = :customerId)";
        final Map<String, Object> params = Map.ofEntries(
                Map.entry("productId", productId),
                Map.entry("customerId", customerId));
        return namedJdbcTemplate.queryForObject(sql, params, boolean.class);
    }

    public void deleteCartItem(final Long id) {

        final String query = "DELETE FROM cart_item WHERE id = :id";
        int rowCount = namedJdbcTemplate.update(query, Map.of("id", id));
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }
}
