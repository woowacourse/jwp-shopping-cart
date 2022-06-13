package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.exception.shoppingcart.InvalidCartItemException;
import woowacourse.shoppingcart.dao.dto.CartItem;
import woowacourse.shoppingcart.domain.Product;

@Repository
public class CartItemDao {

    private static RowMapper<CartItem> cartRowMapper = (rs, rowNum) -> new CartItem(
            rs.getLong("cart_item.id"),
            new Product(rs.getLong("product.id"),
                    rs.getString("product.name"),
                    rs.getInt("product.price"),
                    rs.getString("product.image_url")),
            rs.getInt("cart_item.quantity")
    );

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

    public List<Long> findIdsByLoginId(final String loginId) {
        final String sql = "SELECT cart_item.id "
                + "FROM cart_item INNER JOIN customer ON cart_item.customer_id = customer.id "
                + "WHERE customer.login_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("cart_item.id"), loginId);
    }

    public Long findProductIdById(final Long cartId) {
        try {
            final String sql = "SELECT product_id FROM cart_item WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getLong("product_id"), cartId);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public CartItem findCartByCartId(Long cartId) {
        try {
            final String sql =
                    "SELECT cart_item.id, product.id , product.name, product.price, product.image_url, cart_item.quantity "
                            + "FROM cart_item INNER JOIN product ON cart_item.product_id = product.id "
                            + "WHERE cart_item.id = ?";
            return jdbcTemplate.queryForObject(sql, cartRowMapper, cartId);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public List<CartItem> findCartsByLoginId(String loginId) {
        final String sql =
                "SELECT cart_item.id, product.id , product.name, product.price, product.image_url, cart_item.quantity "
                        + "FROM cart_item INNER JOIN product ON cart_item.product_id = product.id "
                        + "INNER JOIN customer ON cart_item.customer_id = customer.id "
                        + "WHERE customer.login_id = ?";
        return jdbcTemplate.query(sql, cartRowMapper, loginId);
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

    public void deleteAllCart(final Long customerId) {
        final String sql = "DELETE FROM cart_item WHERE customer_id = ?";

        final int rowCount = jdbcTemplate.update(sql, customerId);
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }

    public void updateQuantity(Long cartId, int quantity) {
        final String query = "UPDATE cart_item SET quantity = ? WHERE id = ?";

        final int rowCount = jdbcTemplate.update(query, quantity, cartId);
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }
}
