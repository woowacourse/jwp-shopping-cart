package woowacourse.shoppingcart.repository.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.exception.ResourceNotFoundException;

@Repository
public class CartItemDao {

    private static final RowMapper<CartItem> ROW_MAPPER = (resultSet, rowNum) -> new CartItem(
            resultSet.getLong("id"),
            resultSet.getLong("customer_id"),
            resultSet.getLong("product_id"),
            resultSet.getInt("quantity")
    );

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CartItemDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

//    public List<Long> findProductIdsByCustomerId(final Long customerId) {
//        final String sql = "SELECT product_id FROM cart_item WHERE customer_id = ?";
//
//        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("product_id"), customerId);
//    }
//
//    public List<Long> findIdsByCustomerId(final Long customerId) {
//        final String sql = "SELECT id FROM cart_item WHERE customer_id = ?";
//
//        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("id"), customerId);
//    }
//
//    public Long findProductIdById(final Long cartId) {
//        try {
//            final String sql = "SELECT product_id FROM cart_item WHERE id = ?";
//            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getLong("product_id"), cartId);
//        } catch (EmptyResultDataAccessException e) {
//            throw new InvalidCartItemException();
//        }
//    }
//
//    public Long addCartItem(final Long customerId, final Long productId) {
//        final String sql = "INSERT INTO cart_item(customer_id, product_id) VALUES(?, ?)";
//        final KeyHolder keyHolder = new GeneratedKeyHolder();
//
//        jdbcTemplate.update(con -> {
//            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
//            preparedStatement.setLong(1, customerId);
//            preparedStatement.setLong(2, productId);
//            return preparedStatement;
//        }, keyHolder);
//        return keyHolder.getKey().longValue();
//    }
//
//    public void deleteCartItem(final Long id) {
//        final String sql = "DELETE FROM cart_item WHERE id = ?";
//
//        final int rowCount = jdbcTemplate.update(sql, id);
//        if (rowCount == 0) {
//            throw new InvalidCartItemException();
//        }
//    }

    // new method

    public Long create(final Long customerId, final Long productId) {
        String query = "insert into cart_item (customer_id, product_id, quantity)"
                + " values (:customerId, :productId, 1)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("productId", productId);
        SqlParameterSource source = new MapSqlParameterSource(params);
        namedParameterJdbcTemplate.update(query, source, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public List<CartItem> findCartItemsByCustomerId(final Long customerId) {
        String query = "select id, customer_id, product_id, quantity from cart_item where customer_id = :customerId";
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        return namedParameterJdbcTemplate.query(query, params, ROW_MAPPER);
    }

    public CartItem findByCustomerIdAndProductId(final Long customerId, final Long productId) {
        String query = "select id, customer_id, product_id, quantity from cart_item"
                + " where customer_id = :customerId and product_id = :productId";
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("productId", productId);
        try {
            return namedParameterJdbcTemplate.queryForObject(query, params, ROW_MAPPER);
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    public void updateCartItem(final Long cartItemId, final int quantity) {
        String query = "update cart_item set quantity = :quantity where id = :cartItemId";
        Map<String, Object> params = new HashMap<>();
        params.put("cartItemId", cartItemId);
        params.put("quantity", quantity);
        int affectedRowCount = namedParameterJdbcTemplate.update(query, params);
        if (affectedRowCount == 0) {
            throw new ResourceNotFoundException("존재하지 않는 장바구니 물품입니다.");
        }
    }
}
