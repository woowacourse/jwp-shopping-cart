package woowacourse.shoppingcart.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CartItemDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CartItemDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Long> findProductIdsByCustomerId(final long customerId) {
        final String sql = "SELECT product_id FROM cart_item WHERE customer_id = :customerId";

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("customerId", customerId);

        return namedParameterJdbcTemplate.query(
                sql,
                new MapSqlParameterSource(parameters),
                (rs, rowNum) -> rs.getLong("product_id")
        );
    }

    public List<Long> findIdsByCustomerId(final long customerId) {
        final String sql = "SELECT id FROM cart_item WHERE customer_id = :customerId";

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("customerId", customerId);

        return namedParameterJdbcTemplate.query(
                sql,
                new MapSqlParameterSource(parameters),
                (rs, rowNum) -> rs.getLong("id")
        );
    }

    public Long findProductIdById(final long id) {
        final String sql = "SELECT product_id FROM cart_item WHERE id = :id";

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", id);

        try {
            return namedParameterJdbcTemplate.queryForObject(sql, new MapSqlParameterSource(parameters), (rs, rowNum) -> rs.getLong("product_id"));
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public Long addCartItem(final Long customerId, final Long productId) {
        final SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName("cart_item")
                .usingGeneratedKeyColumns("id");

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("customer_id", customerId);
        parameters.put("product_id", productId);

        final Number number = simpleJdbcInsert.executeAndReturnKey(parameters);
        return number.longValue();
    }

    public void deleteCartItem(final Long id) {
        final String sql = "DELETE FROM cart_item WHERE id = :id";

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", id);

        final int rowCount = namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(parameters));
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }
}
