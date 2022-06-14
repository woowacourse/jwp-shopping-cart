package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;

@Repository
public class CartItemDao {

    private static final RowMapper<CartItem> CART_ITEM_ROW_MAPPER = (resultSet, rowNum) -> {
        return new CartItem(
                resultSet.getLong("id"),
                resultSet.getInt("quantity"),
                new Product(
                        resultSet.getLong("product_id"),
                        resultSet.getString("p_name"),
                        resultSet.getInt("p_price"),
                        resultSet.getString("p_image_url")
                )
        );
    };

    private final JdbcTemplate jdbcTemplate;

    public CartItemDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long createCartItem(final Long customerId, final Long productId, final Integer quantity) {
        final String sql = "INSERT INTO cart_item (customer_id, product_id, quantity) VALUES (?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setLong(1, customerId);
            preparedStatement.setLong(2, productId);
            preparedStatement.setInt(3, quantity);
            return preparedStatement;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public Long addCartItem(final Long customerId, final Long productId, final Integer quantityToAdd) {
        final String sql = "UPDATE cart_item SET quantity = quantity + ? WHERE customer_id = ?, product_id = ?";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setInt(1, quantityToAdd);
            preparedStatement.setLong(2, customerId);
            preparedStatement.setLong(3, productId);
            return preparedStatement;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public Long addCartItemById(final Long id, final Integer quantityToAdd) {
        final String sql = "UPDATE cart_item SET quantity = quantity + ? WHERE id = ?";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setInt(1, quantityToAdd);
            preparedStatement.setLong(2, id);
            return preparedStatement;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public List<CartItem> findByCustomerId(final Long customerId) {
        final String sql = "SELECT c.id AS id, c.quantity AS quantity, c.product_id AS product_id, "
                + "p.name AS p_name, p.price AS p_price, p.image_url AS p_image_url "
                + "FROM cart_item AS c "
                + "JOIN product AS p "
                + "ON p.id = c.product_id "
                + "WHERE c.customer_id = ?";
        return jdbcTemplate.query(sql, CART_ITEM_ROW_MAPPER, customerId);
    }

    public void deleteCartItem(final Long id) {
        final String sql = "DELETE FROM cart_item WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public boolean existCartItem(final Long customerId, final Long productId) {
        String query = "SELECT EXISTS (SELECT id FROM cart_item WHERE customer_id =? AND product_id = ?)";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, Boolean.class, customerId, productId));
    }

    public boolean existCartItemById(final Long cartItemId) {
        String query = "SELECT EXISTS (SELECT id FROM cart_item WHERE id = ?)";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, Boolean.class, cartItemId));
    }
}
