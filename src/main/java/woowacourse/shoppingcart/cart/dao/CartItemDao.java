package woowacourse.shoppingcart.cart.dao;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.cart.domain.Cart;
import woowacourse.shoppingcart.exception.badrequest.InvalidCartItemException;
import woowacourse.shoppingcart.exception.notfound.NotFoundCartException;

@Repository
public class CartItemDao {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Cart> rowMapper = (resultSet, rowNumber) -> new Cart(
            resultSet.getLong("id"),
            resultSet.getLong("product_id"),
            resultSet.getString("name"),
            resultSet.getInt("price"),
            resultSet.getString("image_url"),
            resultSet.getInt("quantity")
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

    public List<Cart> findAllByCustomerId(final Long customerId) {
        final String sql = ""
                + "SELECT ci.id, "
                + "p.id AS product_id, "
                + "p.name, "
                + "p.price, "
                + "p.image_url, "
                + "ci.quantity "
                + "FROM cart_item AS ci "
                + "INNER JOIN product AS p ON ci.product_id = p.id "
                + "WHERE ci.customer_id = ?";

        return jdbcTemplate.query(sql, rowMapper, customerId);
    }

    public Long findProductIdById(final Long cartId) {
        try {
            final String sql = "SELECT product_id FROM cart_item WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getLong("product_id"), cartId);
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public Long addCartItem(final Long customerId, final Long productId) {
        final String sql = "INSERT INTO cart_item(customer_id, product_id) VALUES(?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            final PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setLong(1, customerId);
            preparedStatement.setLong(2, productId);
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

    public boolean existProduct(Long customerId, Long productId) {
        final String sql = "SELECT EXISTS(SELECT * FROM cart_item WHERE customer_id = ? AND product_id = ?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, customerId, productId);
    }

    public Cart findByProductAndCustomerId(Long productId, Long customerId) {
        try {
            final String sql = ""
                    + "SELECT ci.id, "
                    + "p.id AS product_id, "
                    + "p.name, "
                    + "p.price, "
                    + "p.image_url, "
                    + "ci.quantity "
                    + "FROM cart_item AS ci "
                    + "INNER JOIN product AS p ON ci.product_id = p.id "
                    + "WHERE ci.product_id = ? AND ci.customer_id = ?";
            return jdbcTemplate.queryForObject(sql, rowMapper, productId, customerId);
        } catch (final EmptyResultDataAccessException e) {
            throw new NotFoundCartException();
        }
    }

    public void updateQuantity(Cart cart) {
        final String sql = "UPDATE cart_item SET quantity = ? WHERE id = ?";
        jdbcTemplate.update(sql, cart.getQuantity(), cart.getId());
    }
}
