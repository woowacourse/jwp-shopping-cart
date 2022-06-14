package woowacourse.shoppingcart.dao;

import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

@Repository
public class CartItemDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CartItemDao(JdbcTemplate jdbcTemplate,
                       NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Cart> findCartsByCustomerId(Long id) {
        String sql =
                "SELECT c.id, c.customer_id, p.id as product_id, p.name, p.price, p.image_url, c.quantity FROM cart_item c "
                        + "LEFT JOIN product p "
                        + "ON c.product_id = p.id "
                        + "WHERE customer_id = :id";
        SqlParameterSource parameter = new MapSqlParameterSource("id", id);

        return namedParameterJdbcTemplate.query(sql, parameter, joinRowMapper());
    }

    public Long findProductIdById(final Long cartId) {
        try {
            final String sql = "SELECT product_id FROM cart_item WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getLong("product_id"), cartId);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public void addCartItem(Cart cart) {
        final String sql = "INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)";
        jdbcTemplate.update(sql, cart.getCustomerId(), cart.getProductId(), cart.getQuantity());
    }

    public boolean existByCustomerIdAndProductId(Long customerId, Long productId) {
        final String query = "SELECT EXISTS (SELECT * FROM cart_item WHERE customer_id = ? AND product_id = ?)";
        return jdbcTemplate.queryForObject(query, Boolean.class, customerId, productId);
    }

    public void deleteCartItem(final Long customerId, Long productId) {
        final String sql = "DELETE FROM cart_item WHERE customer_id = ? AND product_id = ?";
        final int rowCount = jdbcTemplate.update(sql, customerId, productId);
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }

    public void deleteCartItems(final Long customerId, List<Long> productId) {
        final String sql = "DELETE FROM cart_item WHERE customer_id = :customer_id AND product_id in (:product_id)";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("customer_id", customerId);
        parameterSource.addValue("product_id", productId);
        int rowCount = namedParameterJdbcTemplate.update(sql, parameterSource);
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }

    public void updateQuantity(Cart cart) {
        final String sql = "UPDATE cart_item SET quantity = ? WHERE customer_id = ? AND product_id = ?";
        final int rowCount = jdbcTemplate.update(sql, cart.getQuantity(), cart.getCustomerId(), cart.getProductId());
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }

    private RowMapper<Cart> joinRowMapper() {
        return (rs, rowNum) -> {
            final Long id = rs.getLong("id");
            final Long customerId = rs.getLong("customer_id");
            final Long productId = rs.getLong("product_id");
            final String name = rs.getString("name");
            final int price = rs.getInt("price");
            final String imageUrl = rs.getString("image_url");
            final int quantity = rs.getInt("quantity");
            return new Cart(id, customerId, new Product(productId, name, price, imageUrl), quantity);
        };
    }
}
