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

    public CartItemDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public CartItem findById(Long id) {
        final String sql = "SELECT ci.id as cart_item_id, ci.product_id as cart_item_product_id, p.name as product_name,"
            + " p.price as product_price, ci.quantity as cart_item_quantity, p.image_url as product_image_url "
            + "FROM cart_item as ci "
            + "LEFT JOIN product AS p ON ci.product_id = p.id "
            + "WHERE ci.id = ?";

        return jdbcTemplate.queryForObject(sql, cartItemJoinProductMapper(), id);
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

    public Long addCartItem(final Long customerId, final Long productId, final int quantity) {
        final String sql = "INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[] {"id"});
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

    public void update(final CartItem cartItem) {
        final String sql = "UPDATE cart_item SET quantity = ? WHERE id = ?";

        jdbcTemplate.update(sql,
            cartItem.getQuantity(),
            cartItem.getId()
        );
    }

    public List<CartItem> findAllByCustomerId(final Long customerId) {
        final String sql =
            "SELECT ci.id as cart_item_id, ci.product_id as cart_item_product_id, p.name as product_name,"
                + " p.price as product_price, ci.quantity as cart_item_quantity, p.image_url as product_image_url "
                + "FROM cart_item as ci "
                + "LEFT JOIN product AS p ON ci.product_id = p.id "
                + "WHERE ci.customer_id = ?";

        return jdbcTemplate.query(sql, cartItemJoinProductMapper(), customerId);
    }

    private RowMapper<CartItem> cartItemJoinProductMapper() {
        return (resultSet, rowNum) -> new CartItem(
            resultSet.getLong("cart_item_id"),
            resultSet.getLong("cart_item_product_id"),
            resultSet.getString("product_name"),
            resultSet.getInt("product_price"),
            resultSet.getInt("cart_item_quantity"),
            resultSet.getString("product_image_url")
        );
    }
}
