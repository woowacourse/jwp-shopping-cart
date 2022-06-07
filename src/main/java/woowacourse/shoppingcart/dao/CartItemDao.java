package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

@Repository
public class CartItemDao {

    private final JdbcTemplate jdbcTemplate;

    public CartItemDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Long> findProductIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT product_id FROM cart_item WHERE customer_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("product_id"), customerId);
    }

    public List<Long> findIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT id FROM cart_item WHERE customer_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("id"), customerId);
    }

    public List<CartItem> findCartItemsByCustomerId(final Long customerId) {
        final String sql = "SELECT id, product_id, quantity FROM cart_item WHERE customer_id = ?";

        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new CartItem(
                        rs.getLong("id"),
                        rs.getLong("product_id"),
                        rs.getInt("quantity")
                ), customerId);
    }

    public Long findProductIdById(final Long cartId) {
        try {
            final String sql = "SELECT product_id FROM cart_item WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getLong("product_id"), cartId);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException("장바구니에 해당하는 상품이 없습니다.");
        }
    }

    public Long addCartItem(final Long customerId, final Long productId, final int quantity) {
        final String sql = "INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setLong(1, customerId);
            preparedStatement.setLong(2, productId);
            preparedStatement.setInt(3, quantity);
            return preparedStatement;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public void deleteCartItem(final Long customerId, final Long productId) {
        final String sql = "DELETE FROM cart_item WHERE customer_id = ? and product_id = ?";

        final int rowCount = jdbcTemplate.update(sql, customerId, productId);
        if (rowCount == 0) {
            throw new InvalidCartItemException("장바구니에 해당하는 상품이 없습니다.");
        }
    }

    public void modifyQuantity(Long customerId, Long productId, int quantity) {
        final String sql = "UPDATE cart_item SET quantity = ? WHERE customer_id = ? and product_id = ?";
        final int rowCount = jdbcTemplate.update(sql, quantity, customerId, productId);
        if (rowCount == 0) {
            throw new InvalidCartItemException("장바구니에 해당하는 상품이 없습니다.");
        }
    }
}
