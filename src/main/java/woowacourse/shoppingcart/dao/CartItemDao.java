package woowacourse.shoppingcart.dao;

import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.exception.dto.ErrorResponse;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.entity.CartItemEntity;
import woowacourse.shoppingcart.exception.NotExistException;

@Repository
public class CartItemDao {

    private static final int INIT_QUANTITY = 1;
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CartItemDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart_item")
                .usingGeneratedKeyColumns("id");
    }

    public Long addCartItem(final Customer customer, final Long productId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("customer_id", customer.getId());
        parameterSource.addValue("product_id", productId);
        parameterSource.addValue("product_id", productId);
        parameterSource.addValue("quantity", INIT_QUANTITY);

        return simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();
    }

    public List<CartItemEntity> findCartItemsByCustomerId(final Long customerId) {
        final String sql = "SELECT id, product_id, quantity FROM cart_item WHERE customer_id = ?";

        return jdbcTemplate.query(sql, (resultSet, rowNum) -> new CartItemEntity(
                resultSet.getLong("id"),
                resultSet.getLong("product_id"),
                resultSet.getInt("quantity")), customerId);
    }

    public CartItemEntity findById(final Long customerId, final Long cartItemId) {
        try {
            final String sql = "SELECT id, product_id, quantity FROM cart_item WHERE customer_id = ? AND id = ?";
            return jdbcTemplate.queryForObject(sql, (resultSet, rowNum) -> new CartItemEntity(
                    resultSet.getLong("id"),
                    resultSet.getLong("product_id"),
                    resultSet.getInt("quantity")), customerId, cartItemId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotExistException("장바구니에 없는 아이템입니다.", ErrorResponse.NOT_EXIST_CART_ITEM);
        }
    }

    public void deleteCartItem(final Long id) {
        final String sql = "DELETE FROM cart_item WHERE id = ?";

        final int rowCount = jdbcTemplate.update(sql, id);
        if (rowCount == 0) {
            throw new NotExistException("장바구니에 없는 아이템입니다.", ErrorResponse.NOT_EXIST_CART_ITEM);
        }
    }

    public void updateQuantity(final Long customerId, final Long cartItemId, final int quantity) {
        final String sql = "UPDATE cart_item SET quantity = ? WHERE customer_id = ? AND id = ?";
        jdbcTemplate.update(sql, quantity, customerId, cartItemId);
    }

    public boolean hasCustomerCartItem(final Long customerId, final Long cartItemId) {
        final String sql = "SELECT exists(SELECT * FROM cart_item WHERE customer_id = ? AND id = ?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, customerId, cartItemId);
    }

    public boolean hasCustomerProductItem(final Long customerId, final Long productId) {
        final String sql = "SELECT exists(SELECT * FROM cart_item WHERE customer_id = ? AND product_id = ?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, customerId, productId);
    }
}
