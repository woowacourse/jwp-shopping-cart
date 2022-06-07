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
import woowacourse.shoppingcart.domain.ThumbnailImage;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

@Repository
public class CartItemDao {
    private static final RowMapper<Cart> CART_ROW_MAPPER = (resultSet, rowNumber) ->
            new Cart(
                    resultSet.getLong("id"),
                    resultSet.getInt("quantity"),
                    new Product(
                            resultSet.getLong("productId"),
                            resultSet.getString("name"),
                            resultSet.getInt("price"),
                            resultSet.getInt("stock_quantity"),
                            new ThumbnailImage(
                                    resultSet.getString("url"),
                                    resultSet.getString("alt")
                            )
                    )
            );

    private final JdbcTemplate jdbcTemplate;

    public CartItemDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Cart getById(final Long cartId) {
        final String sql =
                "SELECT c.id AS id, c.quantity, p.id AS productId, p.name, p.price, p.stock_quantity, p.url, p.alt "
                        + "FROM cart_item AS c, product AS p "
                        + "WHERE c.id = ? AND c.product_id = p.id";

        try {
            return jdbcTemplate.queryForObject(sql, CART_ROW_MAPPER, cartId);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public List<Cart> getAllByCustomerId(final Long customerId) {
        final String sql =
                "SELECT c.id AS id, c.quantity, p.id AS productId, p.name, p.price, p.stock_quantity, p.url, p.alt "
                        + "FROM cart_item AS c, product AS p "
                        + "WHERE c.customer_id = ? AND c.product_id = p.id";

        return jdbcTemplate.query(sql, CART_ROW_MAPPER, customerId);
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

    public Long addCartItem(final Long customerId, final int quantity, final Long productId) {
        final String sql = "INSERT INTO cart_item(customer_id, quantity, product_id) VALUES(?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setLong(1, customerId);
            preparedStatement.setLong(2, quantity);
            preparedStatement.setLong(3, productId);
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

    public void updateCartQuantity(Cart cart) {
        final String sql = "UPDATE cart_item SET quantity = ? WHERE id = ?";

        jdbcTemplate.update(sql, cart.getQuantity(), cart.getId());
    }
}
