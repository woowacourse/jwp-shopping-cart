package woowacourse.shoppingcart.repository.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.Entity.CartEntity;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

@Repository
public class CartItemDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final RowMapper<CartEntity> ROW_MAPPER = (resultSet, rowNum) -> new CartEntity(
            resultSet.getLong("id"),
            resultSet.getLong("customer_id"),
            resultSet.getLong("product_id"),
            resultSet.getInt("quantity")
    );

    public CartItemDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long create(final Long customerId, final Long productId) {
        final String query = "INSERT INTO cart_item(customer_id, product_id, quantity) VALUES (:customer_id, :product_id, :quantity)";

        Map<String, Object> params = new HashMap<>();
        params.put("customer_id", customerId);
        params.put("product_id", productId);
        params.put("quantity", 1);

        SqlParameterSource source = new MapSqlParameterSource(params);

        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(query, source, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public CartEntity findById(Long id) {
        final String query = "SELECT * FROM cart_item WHERE id = :id";
        return jdbcTemplate.queryForObject(query, Map.of("id", id), ROW_MAPPER);
    }

    public List<CartEntity> findCartsByCustomerId(final Long customerId) {
        final String sql = "SELECT * FROM cart_item WHERE customer_id = :customerId";

        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);

        return jdbcTemplate.query(sql, params, ROW_MAPPER);
    }

    public Long findProductIdById(final Long id) {
        final String query = "SELECT product_id FROM cart_item WHERE id = :id";
        try {
            return jdbcTemplate.queryForObject(query, Map.of("id", id), Long.class);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public int deleteById(final Long id) {
        final String query = "DELETE FROM cart_item WHERE id = :id";
        return jdbcTemplate.update(query, Map.of("id", id));
    }

    public int[] deleteByIds(final List<Long> ids) {
        final String query = "DELETE FROM cart_item WHERE id = :id";
        return jdbcTemplate.batchUpdate(query, SqlParameterSourceUtils.createBatch(
                ids.stream()
                        .map(id -> Map.of("id", id))
                        .collect(Collectors.toList()))
        );
    }
}
