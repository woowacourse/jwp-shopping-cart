package woowacourse.shoppingcart.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class CartItemDao {
    private final JdbcTemplate jdbcTemplate;

    public CartItemDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CartItem> findByCustomerId(final Long customerId) {
        final String sql = "SELECT ci.id, pr.id product_id, pr.name product_name, "
                + "pr.price product_price, pr.image_url product_image_url, "
                + "ci.quantity "
                + "FROM cart_item AS ci "
                + "JOIN customer AS cu ON ci.customer_id = cu.id "
                + "JOIN product AS pr ON ci.product_id = pr.id "
                + "WHERE ci.customer_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> mapToCartItem(rs), customerId);
    }

    private CartItem mapToCartItem(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        Long productId = resultSet.getLong("product_id");
        String name = resultSet.getString("product_name");
        int price = resultSet.getInt("product_price");
        String image_url = resultSet.getString("product_image_url");
        int quantity = resultSet.getInt("quantity");

        Product product = new Product(productId, name, price, image_url);
        return new CartItem(id, product, quantity);
    }

    public Long findProductIdById(final Long cartId) {
        try {
            final String sql = "SELECT product_id FROM cart_item WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getLong("product_id"), cartId);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public Long addCartItem(final Long customerId, final Long productId) {
        final String sql = "INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setLong(1, customerId);
            preparedStatement.setLong(2, productId);
            preparedStatement.setInt(3, 1);
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

    public void update(CartItem cartItem) {
        final String sql = "UPDATE cart_item SET quantity = ? WHERE id = ?";

        final int rowCount = jdbcTemplate.update(sql, cartItem.getQuantity(), cartItem.getId());
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }
}
