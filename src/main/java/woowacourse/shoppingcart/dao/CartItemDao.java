package woowacourse.shoppingcart.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class CartItemDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final RowMapper<Long> idRowMapper = (rs, rowNum) -> rs.getLong("id");
    private final RowMapper<Long> productIdRowMapper = (rs, rowNum) -> rs.getLong("product_id");

    public CartItemDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Long> findProductIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT product_id FROM cart_item WHERE customer_id =:customerId";
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("customerId", customerId);
        return namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource(parameters), productIdRowMapper);
    }

    public List<Long> findIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT id FROM cart_item WHERE customer_id = :customerId";
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("customerId", customerId);
        return namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource(parameters), idRowMapper);
    }

    public Optional<Long> findProductIdById(final Long cartId) {
        final String sql = "SELECT product_id FROM cart_item WHERE id = :cartId";
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("cartId", cartId);
        List<Long> query = namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource(parameters),
                productIdRowMapper);
        return Optional.ofNullable(DataAccessUtils.singleResult(query));
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

    public int deleteCartItem(final Long id) {
        final String sql = "DELETE FROM cart_item WHERE id = :id";
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", id);
        return namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(parameters));
    }
}
