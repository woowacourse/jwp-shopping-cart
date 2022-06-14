package woowacourse.shoppingcart.dao;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.CartItem;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class CartItemDao {

    private final static RowMapper<CartItem> CART_ITEM_ROW_MAPPER = (resultSet, rowNum) ->  {
        return new CartItem(
                resultSet.getLong("id"),
                resultSet.getLong("customer_id"),
                resultSet.getLong("product_id"),
                resultSet.getInt("quantity")
        );
    };

    private final JdbcTemplate jdbcTemplate;

    public CartItemDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long addCartItem(final Long customerId, final Long productId, final int quantity) throws DataIntegrityViolationException {
        String sql = "INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setLong(1, customerId);
            preparedStatement.setLong(2, productId);
            preparedStatement.setInt(3, quantity);
            return preparedStatement;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public List<Long> findIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT id FROM cart_item WHERE customer_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("id"), customerId);
    }

    public Optional<CartItem> findCartItemById(final Long cartId) {
        try {
            String sql = "SELECT id, customer_id, product_id, quantity FROM cart_item WHERE id = ?";
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, CART_ITEM_ROW_MAPPER, cartId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void updateQuantity(final Long id, final Long customerId, final int quantity) {
        String sql = "UPDATE cart_item SET quantity = ? WHERE id = ? and customer_id = ?";
        jdbcTemplate.update(sql, quantity, id, customerId);
    }

    public void delete(final Long id) {
        String sql = "DELETE FROM cart_item WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
