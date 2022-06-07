package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

@Repository
public class CartItemDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<CartItem> cartItemRowMapper = ((rs, rowNum) ->
            new CartItem(
                    rs.getLong("id"),
                    rs.getLong("productId"),
                    rs.getString("name"),
                    rs.getInt("price"),
                    rs.getInt("quantity"),
                    rs.getString("imageUrl")
            )
    );

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

    public CartItem findById(final Long id) {
        try {
            final String sql = "SELECT c.id as id, c.product_id as productId, p.name as name, p.price as price, "
                    + "c.quantity as quantity, p.image_url as imageUrl "
                    + "FROM cart_item c "
                    + "JOIN product p ON c.product_id = p.id "
                    + "WHERE c.id = ?";

            return jdbcTemplate.queryForObject(sql, cartItemRowMapper, id);
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

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

    public void updateCartItemQuantity(final Long id, final int quantity) {
        final String sql = "UPDATE cart_item SET quantity = ? WHERE id = ?";

        final int rowCount = jdbcTemplate.update(sql, quantity, id);
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
