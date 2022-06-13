package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

@Repository
public class CartItemDao {

    private final JdbcTemplate jdbcTemplate;

    public CartItemDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<CartItem> cartItemRowMapper = ((rs, rowNum) -> {
        Product product = Product.builder()
                .id(rs.getLong("product_id"))
                .productName(rs.getString("name"))
                .price(rs.getInt("price"))
                .stock(rs.getInt("stock"))
                .imageUrl(rs.getString("image_url"))
                .build();
        return new CartItem(rs.getLong("id"), product, rs.getInt("quantity"));
    });

    public Long save(final Long customerId, final CartItem cartItem) {
        final String sql = "INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setLong(1, customerId);
            preparedStatement.setLong(2, cartItem.getProduct().getId());
            preparedStatement.setInt(3, cartItem.getQuantity());
            return preparedStatement;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public CartItem findById(final Long cartItemId) {
        try {
            final String sql =
                    "SELECT c.id, c.quantity, c.product_id, p.name, p.price, p.stock, p.image_url FROM cart_item c " +
                            "INNER JOIN product p ON c.product_id = p.id " +
                            "WHERE c.id = ?";
            return jdbcTemplate.queryForObject(sql, cartItemRowMapper, cartItemId);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public List<CartItem> findAllByCustomerId(final Long customerId) {
        final String sql =
                "SELECT c.id, c.quantity, c.product_id, p.name, p.price, p.stock, p.image_url FROM cart_item c " +
                        "INNER JOIN product p ON c.product_id = p.id " +
                        "WHERE c.customer_id = ?";
        return jdbcTemplate.query(sql, cartItemRowMapper, customerId);
    }

    public List<CartItem> findByIdsIn(final List<Long> ids) {
        final String in = String.join(",", Collections.nCopies(ids.size(), "?"));
        final String sql =
                "SELECT c.id, c.quantity, c.product_id, p.name, p.price, p.stock, p.image_url FROM cart_item c " +
                        "INNER JOIN product p ON c.product_id = p.id " +
                        "WHERE c.id IN (" + in + ")";
        return jdbcTemplate.query(sql, cartItemRowMapper, ids.toArray());
    }

    public List<Long> findIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT id FROM cart_item WHERE customer_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("id"), customerId);
    }

    public List<Long> findProductIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT product_id FROM cart_item WHERE customer_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("product_id"), customerId);
    }

    public boolean isCartItemExistByCustomer(final Long cartItemId, final Long customerId) {
        final String sql = "SELECT EXISTS(select id from cart_item WHERE id = ? AND customer_id = ?)";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, cartItemId, customerId));
    }

    public void updateQuantity(final CartItem cartItem, final Long customerId) {
        final String query = "UPDATE cart_item SET quantity = ? "
                + "WHERE id = ? AND customer_id = ?";
        int rowCount = jdbcTemplate.update(query, cartItem.getQuantity(), cartItem.getId(), customerId);
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }

    public void deleteById(final Long id, final Long customerId) {
        final String sql = "DELETE FROM cart_item "
                + "WHERE id = ? AND customer_id = ?";
        final int rowCount = jdbcTemplate.update(sql, id, customerId);
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }

    public void deleteByIdsIn(final List<Long> ids, final Long customerId) {
        final String in = String.join(",", Collections.nCopies(ids.size(), "?"));
        final String sql = "DELETE FROM cart_item "
                + "WHERE id iN (" + in + ") AND customer_id = ?";
        ids.add(customerId);
        final int rowCount = jdbcTemplate.update(sql, ids.toArray());
        if (rowCount != ids.size() - 1) {
            throw new InvalidCartItemException();
        }
    }
}
