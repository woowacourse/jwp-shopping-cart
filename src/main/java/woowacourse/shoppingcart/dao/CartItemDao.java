package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.exception.badRequest.InvalidCartItemException;

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

    public Long findProductIdById(final Long cartId) {
        try {
            final String sql = "SELECT product_id FROM cart_item WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getLong("product_id"), cartId);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public List<Cart> findCartsByCustomerId(final Long customerId) {
        final String sql =
                "SELECT cart_item.id, cart_item.product_id, cart_item.quantity, product.name, product.price, product.image_url "
                        + "FROM cart_item "
                        + "INNER JOIN product "
                        + "ON cart_item.product_id = product.id "
                        + "WHERE cart_item.customer_id = ? ";

        return (List<Cart>) jdbcTemplate.query(sql, (rs, rowNum) -> {
            return new Cart(
                    rs.getLong("id"),
                    rs.getLong("product_Id"),
                    rs.getInt("quantity"),
                    rs.getString("name"),
                    rs.getInt("price"),
                    rs.getString("image_url")
            );
        }, customerId);
    }

    public Long addCartItem(final Long customerId, final Long productId) {
        final String sql = "INSERT INTO cart_item(customer_id, quantity, product_id) VALUES(?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setLong(1, customerId);
            preparedStatement.setLong(2, 1);
            preparedStatement.setLong(3, productId);
            return preparedStatement;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public void updateCartItem(final Integer quantity, final Long customerId, final Long productId) {
        final String sql = "update cart_item set quantity = ? where customer_id = ? and product_id = ?";

        final int rowCount = jdbcTemplate.update(sql, quantity, customerId, productId);
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }

    public void deleteCartItem(final Long id) {
        final String sql = "DELETE FROM cart_item WHERE id = ?";

        final int rowCount = jdbcTemplate.update(sql, id);
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }
}
