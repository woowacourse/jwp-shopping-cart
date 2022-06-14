package woowacourse.shoppingcart.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.application.exception.InvalidCartItemException;
import woowacourse.shoppingcart.domain.Cart;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class CartItemDao {
    private final JdbcTemplate jdbcTemplate;

    private RowMapper<Cart> rowMapper() {
        return (rs, rowNum) -> {
            Long id = rs.getLong("id");
            Long productId = rs.getLong("product_id");
            String name = rs.getString("name");
            int price = rs.getInt("price");
            int quantity = rs.getInt("quantity");
            String imageUrl = rs.getString("image_url");
            return new Cart(id, productId, name, price, imageUrl, quantity);
        };
    }

    public CartItemDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Long> findProductIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT product_id FROM cart_item WHERE customer_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("product_id"), customerId);
    }

    public List<Cart> findAllByCustomerId(final Long customerId) {
        final String sql = "SELECT ci.id, ci.product_id, ci.quantity, p.name, p.price, p.image_url " +
                "FROM cart_item ci " +
                "JOIN product p ON ci.product_id = p.id WHERE ci.customer_id = ?";

        return jdbcTemplate.query(sql, rowMapper(), customerId);
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
        final String sql = "INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setLong(1, customerId);
            preparedStatement.setLong(2, productId);
            preparedStatement.setLong(3, 1);
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

    public void updateQuantity(final Long cartId, final int quantity) {
        final String sql = "UPDATE cart_item SET quantity = ? WHERE id = ?";
        jdbcTemplate.update(sql, quantity, cartId);
    }

    public Optional<Cart> findIdAndQuantityByProductId(final Long productId, final Long customerId) {
        final String sql = "SELECT id, quantity FROM cart_item WHERE product_id = ? AND customer_id = ?";
        final List<Cart> carts = jdbcTemplate.query(sql, ((rs, rowNum) -> {
            Long id = rs.getLong("id");
            int quantity = rs.getInt("quantity");
            return new Cart(id, productId, null, 0, null, quantity);
        }), productId, customerId);
        return Optional.ofNullable(DataAccessUtils.singleResult(carts));
    }
}
