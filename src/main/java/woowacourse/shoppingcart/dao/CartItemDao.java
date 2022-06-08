package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

@Repository
public class CartItemDao {

    private final JdbcTemplate jdbcTemplate;

    public CartItemDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Cart> findAllJoinProductByCustomerId(final Long customerId) {
        final String sql =
                "SELECT c.id, c.quantity, c.product_id, p.name, p.price, p.image_url FROM cart_item AS c "
                        + "INNER JOIN product AS p ON p.id = c.product_id "
                        + "WHERE c.customer_id = ?";

        return jdbcTemplate.query(sql, rowMapper(), customerId);
    }

    private RowMapper<Cart> rowMapper() {
        return (rs, rowNum) -> {
            Long id = rs.getLong("id");
            final int quantity = rs.getInt("quantity");
            final long product_id = rs.getLong("product_id");
            final String productName = rs.getString("name");
            final int productPrice = rs.getInt("price");
            final String productImageUrl = rs.getString("image_url");
            return new Cart(id, quantity, new Product(product_id, productName, productPrice, productImageUrl));
        };
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

    public Long addCartItem(final Long customerId, final Long productId) {
        final String sql = "INSERT INTO cart_item(quantity, customer_id, product_id) VALUES(?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setInt(1, 1);
            preparedStatement.setLong(2, customerId);
            preparedStatement.setLong(3, productId);
            return preparedStatement;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public void updateQuantity(final int quantity, final Long id) {
        final String query = "UPDATE cart_item SET quantity = ? WHERE id = ?";
        final int update = jdbcTemplate.update(query, quantity, id);
        if (update == 0) {
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
