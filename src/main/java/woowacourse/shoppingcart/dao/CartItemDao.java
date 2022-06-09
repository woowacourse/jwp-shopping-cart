package woowacourse.shoppingcart.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.cartitem.CartItem;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

import java.util.List;

@Repository
public class CartItemDao {

    private static final int DEFAULT_QUANTITY = 1;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CartItemDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Long> findProductIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT product_id FROM cart_item WHERE customer_id = :customerid";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("customerid", customerId);
        return namedParameterJdbcTemplate.query(sql, parameterSource, (rs, rowNum) -> rs.getLong("product_id"));
    }

    public List<Long> findIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT id FROM cart_item WHERE customer_id = :customerid";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("customerid", customerId);

        return namedParameterJdbcTemplate.query(sql, parameterSource, (rs, rowNum) -> rs.getLong("id"));
    }

    public Long findProductIdByCartId(final Long cartId) {
        try {
            final String sql = "SELECT product_id FROM cart_item WHERE id = :cartid";
            MapSqlParameterSource parameterSource = new MapSqlParameterSource("cartid", cartId);
            return namedParameterJdbcTemplate.queryForObject(sql, parameterSource, (rs, rowNum) -> rs.getLong("product_id"));
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public Long addCartItem(final Long customerId, final Long productId) {
        final String sql = "INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(:customerid, :productid, :quantity)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("customerid", customerId);
        parameterSource.addValue("productid", productId);
        parameterSource.addValue("quantity", DEFAULT_QUANTITY);
        namedParameterJdbcTemplate.update(sql, parameterSource, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public void deleteCartItem(final Long id) {
        final String sql = "DELETE FROM cart_item WHERE id = :id";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
        final int rowCount = namedParameterJdbcTemplate.update(sql, parameterSource);
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }

    public int findQuantityById(Long cartId) {
        try {
            final String sql = "SELECT quantity FROM cart_item WHERE id = :id";
            MapSqlParameterSource parameterSource = new MapSqlParameterSource("id", cartId);
            return namedParameterJdbcTemplate.queryForObject(sql, parameterSource, (rs, rowNum) -> rs.getInt("quantity"));
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public void updateQuantity(Long customerId, CartItem cartItem) {
        final String sql = "UPDATE cart_item set quantity=:quantity where customer_id=:customerid and id = :id";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("quantity", cartItem.getQuantity());
        parameterSource.addValue("id", cartItem.getId());
        parameterSource.addValue("customerid", customerId);
        namedParameterJdbcTemplate.update(sql, parameterSource);
    }

    public int findQuantityByUserAndProduct(Long customerId, Long productId) {
        try {
            final String sql = "SELECT quantity FROM cart_item WHERE customer_id = :customerid and product_id = :productid";
            MapSqlParameterSource parameterSource = new MapSqlParameterSource("customerid", customerId);
            parameterSource.addValue("productid", productId);
            return namedParameterJdbcTemplate.queryForObject(sql, parameterSource, (rs, rowNum) -> rs.getInt("quantity"));
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    public Long findIdByUserAndProduct(Long customerId, Long productId) {
        try {
            final String sql = "SELECT id FROM cart_item WHERE customer_id = :customerid and product_id = :productid";
            MapSqlParameterSource parameterSource = new MapSqlParameterSource("customerid", customerId);
            parameterSource.addValue("productid", productId);
            return namedParameterJdbcTemplate.queryForObject(sql, parameterSource, (rs, rowNum) -> rs.getLong("id"));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
