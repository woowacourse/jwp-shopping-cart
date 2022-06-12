package woowacourse.shoppingcart.dao;

import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.dto.AddCartItemRequest;
import woowacourse.shoppingcart.dto.UpdateCartItemRequest;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

@Repository
public class CartItemDao {

    private static final String CUSTOMER_ID = "customer_id";
    private static final String PRODUCT_ID = "product_id";
    private static final String QUANTITY = "quantity";
    private static final String CHECKED = "checked";
    private static final String CART_ITEM_ID = "id";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;

    public CartItemDao(final DataSource dataSource, final JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Long> findIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT id FROM cart_item WHERE customer_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong(CART_ITEM_ID), customerId);
    }

    public Long findProductIdById(final Long cartId) {
        try {
            final String sql = "SELECT product_id FROM cart_item WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getLong(PRODUCT_ID), cartId);
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public void addCartItem(final Long customerId, final AddCartItemRequest addCartItemRequest) {
        final String sql = "INSERT INTO cart_item(customer_id, product_id, quantity, checked)"
                + "VALUES(:customer_id, :product_id, :quantity, :checked)";

        final var paramSource = Map.of(
                CUSTOMER_ID, customerId,
                PRODUCT_ID, addCartItemRequest.getProductId(),
                QUANTITY, addCartItemRequest.getQuantity(),
                CHECKED, addCartItemRequest.getChecked()
        );

        namedParameterJdbcTemplate.update(sql, paramSource);
    }

    public void deleteCartItem(final Long cartItemId) {
        final String sql = "DELETE FROM cart_item WHERE id = ?";

        final int rowCount = jdbcTemplate.update(sql, cartItemId);
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }

    public List<CartItem> findByCustomerId(final long customerId) {
        final var sql = "SELECT * FROM cart_item WHERE customer_id = :customer_id";

        final RowMapper<CartItem> rowMapper = (rs, rowNum) -> {
            final var id = rs.getLong(CART_ITEM_ID);
            final var product_id = rs.getLong(PRODUCT_ID);
            final var quantity = rs.getInt(QUANTITY);
            final var checked = rs.getBoolean(CHECKED);
            return new CartItem(id, product_id, quantity, checked);
        };

        return namedParameterJdbcTemplate.query(sql, Map.of(CUSTOMER_ID, customerId), rowMapper);
    }

    public void deleteAllByCustomerId(final Long customerId) {
        final String sql = "DELETE FROM cart_item WHERE customer_id = ?";

        jdbcTemplate.update(sql, customerId);
    }

    public void update(final long customerId, final UpdateCartItemRequest updateCartItemRequest) {
        final String sql = "UPDATE cart_item SET quantity = (:quantity), checked = (:checked) WHERE customer_id = (:customer_id) AND id = (:id)";

        final var paramSource = Map.of(
                CART_ITEM_ID, updateCartItemRequest.getId(),
                CUSTOMER_ID, customerId,
                QUANTITY, updateCartItemRequest.getQuantity(),
                CHECKED, updateCartItemRequest.getChecked()
        );

        namedParameterJdbcTemplate.update(sql, paramSource);
    }
}
