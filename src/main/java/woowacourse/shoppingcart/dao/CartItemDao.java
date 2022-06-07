package woowacourse.shoppingcart.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class CartItemDao {
    private static final RowMapper<CartItem> CART_ITEM_MAPPER = (rs, rowNum) -> {
        var id = rs.getLong("id");
        var customerId = rs.getLong("customer_id");
        var productId = rs.getLong("product_id");
        var quantity = rs.getInt("quantity");
        var checked = rs.getBoolean("checked");
        return new CartItem(id, customerId, productId, quantity, checked);
    };

    private final JdbcTemplate jdbcTemplate;

    public CartItemDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CartItem> findCartItemByUserId(Long customerId) {
        String sql = "SELECT * FROM cart_item WHERE customer_id = ?";
        return jdbcTemplate.query(sql, CART_ITEM_MAPPER, customerId);
    }
}
