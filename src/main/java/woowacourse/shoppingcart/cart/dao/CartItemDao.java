package woowacourse.shoppingcart.cart.dao;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.cart.domain.Cart;
import woowacourse.shoppingcart.cart.domain.CartItem;
import woowacourse.shoppingcart.cart.exception.badrequest.NoExistCartItemException;
import woowacourse.shoppingcart.product.domain.Product;

@Repository
public class CartItemDao {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<CartItem> rowMapper = (resultSet, rowNumber) -> {
        final Product product = new Product(
                resultSet.getLong("product_id"),
                resultSet.getString("name"),
                resultSet.getInt("price"),
                resultSet.getString("image_url")
        );
        return new CartItem(
                resultSet.getLong("id"),
                product,
                resultSet.getInt("quantity")
        );
    };

    public CartItemDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Cart findCartByCustomerId(final Long customerId) {
        final String sql = ""
                + "SELECT ci.id, "
                + "p.id AS product_id, "
                + "p.name, "
                + "p.price, "
                + "p.image_url, "
                + "ci.quantity "
                + "FROM cart_item AS ci "
                + "INNER JOIN product AS p ON ci.product_id = p.id "
                + "WHERE ci.customer_id = ?";

        final List<CartItem> cartItems = jdbcTemplate.query(sql, rowMapper, customerId);
        return new Cart(cartItems);
    }

    public Long addCartItem(final Long customerId, final Long productId) {
        final String sql = "INSERT INTO cart_item(customer_id, product_id) VALUES(?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            final PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setLong(1, customerId);
            preparedStatement.setLong(2, productId);
            return preparedStatement;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public void deleteCartItem(final Long cartItemId) {
        final String sql = "DELETE FROM cart_item WHERE id = ?";

        final int rowCount = jdbcTemplate.update(sql, cartItemId);
        if (rowCount == 0) {
            throw new NoExistCartItemException();
        }
    }

    public boolean existProduct(final Long customerId, final Long productId) {
        final String sql = "SELECT EXISTS(SELECT * FROM cart_item WHERE customer_id = ? AND product_id = ?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, customerId, productId);
    }

    public CartItem findByProductAndCustomerId(final Long productId, final Long customerId) {
        try {
            final String sql = ""
                    + "SELECT ci.id, "
                    + "p.id AS product_id, "
                    + "p.name, "
                    + "p.price, "
                    + "p.image_url, "
                    + "ci.quantity "
                    + "FROM cart_item AS ci "
                    + "INNER JOIN product AS p ON ci.product_id = p.id "
                    + "WHERE ci.product_id = ? AND ci.customer_id = ?";
            return jdbcTemplate.queryForObject(sql, rowMapper, productId, customerId);
        } catch (final EmptyResultDataAccessException e) {
            throw new NoExistCartItemException();
        }
    }

    public void updateQuantity(final CartItem cartItem) {
        final String sql = "UPDATE cart_item SET quantity = ? WHERE id = ?";
        jdbcTemplate.update(sql, cartItem.getQuantity(), cartItem.getId());
    }
}
