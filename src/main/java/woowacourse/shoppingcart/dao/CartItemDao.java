package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import woowacourse.shoppingcart.domain.cart.CartItem;
import woowacourse.shoppingcart.entity.CartItemEntity;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

@Repository
public class CartItemDao {
    private final RowMapper<CartItemEntity> cartItemEntityRowMapper = (resultSet, rowNum) -> new CartItemEntity(
        resultSet.getLong("id"),
        resultSet.getLong("customer_id"),
        resultSet.getLong("product_id"),
        resultSet.getInt("quantity")
    );

    private final JdbcTemplate jdbcTemplate;

    public CartItemDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long addCartItem(final long customerId, final CartItem cartItem) {
        final String query = "INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(query, new String[] {"id"});
            preparedStatement.setLong(1, customerId);
            preparedStatement.setLong(2, cartItem.getProductId());
            preparedStatement.setLong(3, cartItem.getQuantity());
            return preparedStatement;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public List<CartItemEntity> findCartItemsByCustomerId(long customerId) {
        final String sql = "SELECT * FROM cart_item WHERE customer_id = ?";
        return jdbcTemplate.query(sql, cartItemEntityRowMapper, customerId);
    }

    public CartItemEntity findCartItemById(long id) {
        try {
            final String sql = "SELECT * FROM cart_item WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, cartItemEntityRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public void update(CartItem updateCartItem) {
        final String sql = "UPDATE cart_item SET quantity = ? where id = ?";
        jdbcTemplate.update(sql, updateCartItem.getQuantity(), updateCartItem.getId());
    }

    public void delete(Long id) {
        final String sql = "DELETE FROM cart_item WHERE id = ? ";
        jdbcTemplate.update(sql, id);
    }
}
