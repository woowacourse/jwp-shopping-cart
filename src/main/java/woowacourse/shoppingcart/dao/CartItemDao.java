package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.dto.cart.CartItemCreateRequest;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

@Repository
public class CartItemDao {

    private final JdbcTemplate jdbcTemplate;

    public CartItemDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CartItem> findCartItemsByCustomerId(final Long customerId) {
        final String sql = "SELECT id, product_id, customer_id, count FROM cart_item WHERE customer_id = ?";

        return jdbcTemplate.query(sql, rowMapper(), customerId);
    }

    public Long addCartItem(final Long customerId, final CartItemCreateRequest cartItemCreateRequest) {
        final String sql = "INSERT INTO cart_item(customer_id, product_id, count) VALUES(?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setLong(1, customerId);
            preparedStatement.setLong(2, cartItemCreateRequest.getProductId());
            preparedStatement.setInt(3, cartItemCreateRequest.getCount());
            return preparedStatement;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public void deleteCartItem(final Long id) {
        final String sql = "DELETE FROM cart_item WHERE id = ?";

        final int rowCount = jdbcTemplate.update(sql, id);
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }

    public void updateCount(final Long customerId, final Long productId, final int newCount) {
        final String sql = "UPDATE cart_item SET count = ? WHERE customer_id = ? AND product_id = ?";
        jdbcTemplate.update(sql, newCount, customerId, productId);
    }

    public void deleteCartItemByCustomerIdAndProductId(final Long customerId, final Long productId) {
        final String sql = "DELETE FROM cart_item WHERE customer_id = ? and product_id = ?";

        final int rowCount = jdbcTemplate.update(sql, customerId, productId);
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }

    public boolean existIdByCustomerIdAndProductId(Long customerId, Long productId) {
        final String sql = "SELECT EXISTS (SELECT id FROM cart_item WHERE customer_id = ? AND product_id = ?)";

        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, customerId, productId));
    }

    private RowMapper<CartItem> rowMapper() {
        return ((rs, rowNum) -> new CartItem(
                rs.getLong("id"),
                rs.getLong("customer_id"),
                rs.getLong("product_id"),
                rs.getInt("count")
        ));
    }
}
