package woowacourse.shoppingcart.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

@Repository
public class CartItemDao {

    private final NamedParameterJdbcTemplate namedJdbcTemplate;
    private final SimpleJdbcInsert simpleInsert;

    public CartItemDao(NamedParameterJdbcTemplate namedJdbcTemplate, DataSource dataSource) {
        this.namedJdbcTemplate = namedJdbcTemplate;
        this.simpleInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("cart_item")
                .usingGeneratedKeyColumns("id");
    }

    public List<Long> findProductIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT product_id FROM cart_item WHERE customer_id = :customerId";
        final SqlParameterSource parameter = new MapSqlParameterSource(Map.of("customerId", customerId));
        return namedJdbcTemplate.query(sql, parameter, (resultSet, rowNum) ->
                resultSet.getLong("product_id"));
    }

    public List<Long> findIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT id FROM cart_item WHERE customer_id = :customerId";
        final SqlParameterSource parameter = new MapSqlParameterSource(Map.of("customerId", customerId));
        return namedJdbcTemplate.query(sql, parameter, (resultSet, rowNum) -> resultSet.getLong("id"));
    }

    public Long findProductIdById(final Long cartId) {
        try {
            final String sql = "SELECT product_id FROM cart_item WHERE id = :cartId";
            final SqlParameterSource parameter = new MapSqlParameterSource(Map.of("cartId", cartId));
            return namedJdbcTemplate.queryForObject(sql, parameter,
                    (resultSet, rowNum) -> resultSet.getLong("product_id"));
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public Integer findQuantityById(final Long productId) {
        final String sql = "SELECT quantity FROM cart_item WHERE product_id=:productId";
        final SqlParameterSource parameter = new MapSqlParameterSource(Map.of("productId", productId));
        return namedJdbcTemplate.queryForObject(sql, parameter, Integer.class);
    }

    public Long addCartItem(final Long customerId, final Long productId, final int quantity) {
        final String sql = "INSERT INTO cart_item(customer_id, product_id) VALUES(?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        final Map<String, Object> params = new HashMap<>(Map.of(
                "customer_id", customerId,
                "product_id", productId,
                "quantity", quantity));
        return simpleInsert.executeAndReturnKey(params).longValue();
    }

    public Long findIdByProductIdAndCustomerId(final Long customerId, final Long productId) {
        try {
            final String sql = "SELECT id FROM cart_item WHERE customer_id=:customerId AND product_id=:productId";
            final SqlParameterSource parameter = new MapSqlParameterSource(Map.of("customerId", customerId,
                    "productId", productId));
            return namedJdbcTemplate.queryForObject(sql, parameter, Long.class);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public void updateQuantity(final Long customerId, final Long productId, final int quantity) {
        final String sql = "UPDATE cart_item SET quantity=:quantity WHERE customer_id=:customerId AND product_id=:productId";
        final SqlParameterSource parameterSource = new MapSqlParameterSource(Map.ofEntries(
                Map.entry("quantity", quantity),
                Map.entry("customerId", customerId),
                Map.entry("productId", productId)
        ));
        namedJdbcTemplate.update(sql, parameterSource);
    }

    public void deleteCartItem(final Long id) {
        final String sql = "DELETE FROM cart_item WHERE id = :id";
        final int rowCount = namedJdbcTemplate.update(sql, Map.of("id", id));
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }

}
