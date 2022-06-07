package woowacourse.shoppingcart.dao;

import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.exception.dto.ErrorResponse;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.exception.NotExistCartItemException;

@Repository
public class CartItemDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final ProductDao productDao;

    public CartItemDao(final JdbcTemplate jdbcTemplate, final ProductDao productDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart_item")
                .usingGeneratedKeyColumns("id");
        this.productDao = productDao;
    }

    public Long addCartItem(final Customer customer, final Long productId, final int quantity) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("customer_id", customer.getId());
        parameterSource.addValue("product_id", productId);
        parameterSource.addValue("product_id", productId);
        parameterSource.addValue("quantity", quantity);

        return simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();
    }

    public List<CartItem> findCartItemsByCustomerId(final Long customerId) {
        final String sql = "SELECT id, product_id, quantity FROM cart_item WHERE customer_id = ?";

        return jdbcTemplate.query(sql, (resultSet, rowNum) -> new CartItem(
                resultSet.getLong("id"),
                productDao.findProductById(resultSet.getLong("product_id")),
                resultSet.getInt("quantity")), customerId);
    }

    public CartItem findById(final Long customerId, final Long cartItemId) {
        try {
            final String sql = "SELECT id, product_id, quantity FROM cart_item WHERE customer_id = ? AND id = ?";
            return jdbcTemplate.queryForObject(sql, (resultSet, rowNum) -> new CartItem(
                    resultSet.getLong("id"),
                    productDao.findProductById(resultSet.getLong("product_id")),
                    resultSet.getInt("quantity")), customerId, cartItemId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotExistCartItemException("장바구니에 없는 아이템입니다.", ErrorResponse.Not_EXIST_CART_ITEM);
        }
    }

    public void deleteCartItem(final Long id) {
        final String sql = "DELETE FROM cart_item WHERE id = ?";

        final int rowCount = jdbcTemplate.update(sql, id);
        if (rowCount == 0) {
            throw new NotExistCartItemException("장바구니에 없는 아이템입니다.", ErrorResponse.Not_EXIST_CART_ITEM);
        }
    }

    public boolean validateCustomerCartItem(final Long customerId, final Long cartItemId) {
        final String sql = "SELECT exists(SELECT * FROM cart_item WHERE customer_id = ? AND id = ?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, customerId, cartItemId);
    }

    public void updateQuantity(final Long customerId, final Long cartItemId, final int quantity) {
        final String sql = "UPDATE cart_item SET quantity = ? WHERE customer_id = ? AND id = ?";
        jdbcTemplate.update(sql, quantity, customerId, cartItemId);
    }
}
