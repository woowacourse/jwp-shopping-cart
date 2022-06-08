package woowacourse.shoppingcart.dao;

import org.springframework.dao.DataIntegrityViolationException;
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

    public Optional<CartItem> findCartItemById(final Long cartId) {
        try {
            String sql = "SELECT id, customer_id, product_id, quantity FROM cart_item WHERE id = ?";
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, CART_ITEM_ROW_MAPPER, cartId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
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
