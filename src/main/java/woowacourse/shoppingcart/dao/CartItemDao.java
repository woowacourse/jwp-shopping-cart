package woowacourse.shoppingcart.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import woowacourse.shoppingcart.exception.InvalidCartItemException;

@Repository
public class CartItemDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final JdbcTemplate jdbcTemplate;

    public CartItemDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart_item")
                .usingGeneratedKeyColumns("id");
    }

    public List<Long> findProductIdsByCustomerId(Long customerId) {
        String sql = "SELECT product_id FROM cart_item WHERE customer_id = :customerId";
        SqlParameterSource parameterSource = new MapSqlParameterSource("customerId", customerId);
        return namedParameterJdbcTemplate.query(sql, parameterSource, (rs, rowNum) -> rs.getLong("product_id"));
    }

    public List<Long> findIdsByCustomerId(Long customerId) {
        String sql = "SELECT id FROM cart_item WHERE customer_id = :customerId";
        SqlParameterSource parameterSource = new MapSqlParameterSource("customerId", customerId);
        return namedParameterJdbcTemplate.query(sql, parameterSource, (rs, rowNum) -> rs.getLong("id"));
    }

    public Optional<Long> findProductIdById(Long cartItemId) {
        try {
            String sql = "SELECT product_id FROM cart_item WHERE id = :id";
            SqlParameterSource parameterSource = new MapSqlParameterSource("id", cartItemId);
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(sql, parameterSource, (rs, rowNum) -> rs.getLong("product_id")));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Integer> findQuantityById(Long cartItemId) {
        try {
            String sql = "SELECT quantity FROM cart_item WHERE id = :id";
            SqlParameterSource parameterSource = new MapSqlParameterSource("id", cartItemId);
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(sql, parameterSource, Integer.class));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Long addCartItem(Long customerId, Long productId, int quantity) {
        SqlParameterSource parameterSource = new MapSqlParameterSource("customerId", customerId)
                .addValue("productId", productId)
                .addValue("quantity", quantity);
        return simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();
    }

    public void deleteCartItem(Long cartItemId) {
        String sql = "DELETE FROM cart_item WHERE id = ?";

        int rowCount = jdbcTemplate.update(sql, cartItemId);
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }

    public void addQuantity(Long cartItemId, int quantity) {
        String sql = "UPDATE cart_item SET quantity = quantity + :quantity WHERE id = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", cartItemId)
                .addValue("quantity", quantity);
        namedParameterJdbcTemplate.update(sql, parameterSource);
    }

    public void updateQuantity(Long cartItemId, int quantity) {
        String sql = "UPDATE cart_item SET quantity = :quantity WHERE id = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", cartItemId)
                .addValue("quantity", quantity);
        namedParameterJdbcTemplate.update(sql, parameterSource);
    }

    public Optional<Long> findIdByProductId(Long productId) {
        try {
            String sql = "SELECT id FROM cart_item WHERE product_id = :productId";
            SqlParameterSource parameterSource = new MapSqlParameterSource("productId", productId);
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(sql, parameterSource, Long.class));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
