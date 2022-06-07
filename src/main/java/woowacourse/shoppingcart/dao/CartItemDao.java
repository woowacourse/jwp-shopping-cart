package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Cart;
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

//    public List<Cart> findCartItemsByCustomerId(final Long customerId) {
//        final String sql = "SELECT cart_item.id as id, product.product_id as product_id, product.name as name, product.price as price, product.image_url as image_url, quantity FROM cart_item "
//                + "JOIN product ON cart_item.product_id = product.id  "
//                + "WHERE cart_item.customer_id = ?";
//
//        return jdbcTemplate.query(sql,
//                (rs, rowNum) -> new Cart(
//                        rs.getLong("id"),
//                        rs.getLong("product_id"),
//                        rs.getString("name"),
//                        rs.getInt("price"),
//                        rs.getString("image_url"),
//                        rs.getInt("quantity")
//                ), customerId);
//    }

    public Long findProductIdById(final Long cartId) {
        try {
            final String sql = "SELECT product_id FROM cart_item WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getLong("product_id"), cartId);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
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

    public void deleteCartItem(final Long id) {
        final String sql = "DELETE FROM cart_item WHERE id = ?";

        final int rowCount = jdbcTemplate.update(sql, id);
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }
}
