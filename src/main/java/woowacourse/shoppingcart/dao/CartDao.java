package woowacourse.shoppingcart.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class CartDao {
    private static final int DEFAULT_ADD_ITEM_QUANTITY = 1;

    private static final RowMapper<CartItem> CART_ROW_MAPPER = (resultSet, rowNum) -> CartItem.of(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getInt("price"),
            resultSet.getString("thumbnail"),
            resultSet.getInt("quantity")
    );

    private final JdbcTemplate jdbcTemplate;

    public CartDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CartItem> findCartsByCustomerId(final Long customerId) {
        final String sql = "SELECT product.id as id, " +
                "product.name as name, " +
                "product.price as price, " +
                "product.thumbnail as thumbnail, " +
                "cart_item.quantity as quantity " +
                "FROM cart_item " +
                "INNER JOIN product " +
                "ON cart_item.product_id = product.id " +
                "WHERE cart_item.customer_id = ?";

        return jdbcTemplate.query(sql, CART_ROW_MAPPER, customerId);
    }

    public List<Long> findProductIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT product_id FROM cart_item WHERE customer_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("product_id"), customerId);
    }

    public Optional<CartItem> findCartItemByProductId(final Long productId, final Long customerId) {
        try {
            final String sql = "SELECT product.id as id, " +
                    "product.name as name, " +
                    "product.price as price, " +
                    "product.thumbnail as thumbnail, " +
                    "cart_item.quantity as quantity " +
                    "FROM cart_item " +
                    "INNER JOIN product " +
                    "ON cart_item.product_id = product.id " +
                    "WHERE cart_item.product_id = ? and cart_item.customer_id = ?";

            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, CART_ROW_MAPPER, productId, customerId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Long addCartItem(final Long customerId, final Long productId) {
        final String sql = "INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setLong(1, customerId);
            preparedStatement.setLong(2, productId);
            preparedStatement.setInt(3, DEFAULT_ADD_ITEM_QUANTITY);
            return preparedStatement;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public void updateCartItemQuantity(final int quantity, final Long productId, final Long customerId) {
        final String sql = "UPDATE cart_item SET quantity = ? WHERE product_id = ? and customer_id = ?";
        final int rowCount = jdbcTemplate.update(sql, quantity, productId, customerId);
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }

    public void deleteCartItem(final Long productId, final Long customerId) {
        final String sql = "DELETE FROM cart_item WHERE product_id = ? and customer_id = ?";

        final int rowCount = jdbcTemplate.update(sql, productId, customerId);
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }

    public void deleteCartByCustomerId(final Long customerId) {
        final String sql = "DELETE FROM cart_item WHERE customer_id = ?";

        final int rowCount = jdbcTemplate.update(sql, customerId);
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }
}
