package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
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

    public Long add(Long customerId, CartItem cartItem) {
        final String sql = "INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setLong(1, customerId);
            preparedStatement.setLong(2, cartItem.getProductId());
            preparedStatement.setInt(3, cartItem.getQuantity());
            return preparedStatement;
        }, keyHolder);

        return Optional.ofNullable(keyHolder.getKey())
                .map(Number::longValue)
                .orElseThrow(() -> new IllegalArgumentException("이미 존재하는 장바구니 아이템입니다."));
    }

    public List<Long> findProductIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT product_id FROM cart_item WHERE customer_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("product_id"), customerId);
    }

    public List<CartItem> findCartItemsByCustomerId(Long customerId) {
        try {
            final String sql = "SELECT cart_item.id, product.id, product.name, product.price, product.image_url, "
                    + "cart_item.quantity FROM cart_item JOIN product ON cart_item.product_id = product.id "
                    + "WHERE cart_item.customer_id = ?";
            return jdbcTemplate.query(sql, (rs, rowNum) ->
                    new CartItem(rs.getLong("cart_item.id"),
                            rs.getLong("product.id"),
                            rs.getString("product.name"),
                            rs.getInt("product.price"),
                            rs.getString("product.image_url"),
                            rs.getInt("cart_item.quantity")), customerId);
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
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

    public void update(CartItem cartItem) {
        final String sql = "UPDATE cart_item SET quantity = ? WHERE id = ?";
        final int updatedRows = jdbcTemplate.update(sql, cartItem.getQuantity(), cartItem.getId());
        checkReflected(updatedRows);
    }

    public void deleteAllByCustomerId(Long customerId) {
        final String sql = "DELETE FROM cart_item WHERE customer_id = ?";
        jdbcTemplate.update(sql, customerId);
    }

    public void delete(final Long id) {
        final String sql = "DELETE FROM cart_item WHERE id = ?";
        final int updatedRows = jdbcTemplate.update(sql, id);
        checkReflected(updatedRows);
    }

    private void checkReflected(int updatedRows) {
        if (updatedRows == 0) {
            throw new InvalidCartItemException();
        }
    }
}
