package woowacourse.shoppingcart.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.InvalidCartItemException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

import javax.sql.DataSource;

@Repository
public class CartItemDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CartItemDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("cart_item")
                .usingGeneratedKeyColumns("id");
    }

    private RowMapper<CartItem> cartRowMapper = (rs, rowNum) -> new CartItem(
            rs.getLong("cart_item.id"),
            new Product(rs.getLong("product.id"),
                rs.getString("product.name"),
                rs.getInt("product.price"),
                rs.getString("product.image_url")),
            rs.getLong("cart_item.quantity"),
            rs.getBoolean("checked")
    );

    public Long addCartItem(Long customerId, Long productId, Long quantity, boolean checked) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("customer_id", customerId)
                .addValue("product_id", productId)
                .addValue("quantity", quantity)
                .addValue("checked", checked);

        return simpleJdbcInsert.executeAndReturnKey(params).longValue();
    }

    public boolean existByCustomerIdAndProductId(Long customerId, Long productId) {
        try {
            String query = "SELECT EXISTS (SELECT * FROM cart_item WHERE customer_id= ? AND product_id = ?)";
            return jdbcTemplate.queryForObject(query, Boolean.class, customerId, productId);
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public List<Long> findIdsByCustomerId(Long customerId) {
        final String sql = "SELECT id FROM cart_item WHERE customer_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("id"), customerId);
    }

    public CartItem findIdByCustomerIdAndProductId(Long customerId, Product product) {
        try {
            String query = "SELECT * FROM cart_item WHERE customer_id= ? AND product_id = ?";
            return jdbcTemplate.queryForObject(query, (rs, rowNum) -> new CartItem(
                            rs.getLong("id"),
                            new Product(product.getId(), product.getName(), product.getPrice(), product.getImageUrl()),
                            rs.getLong("quantity"),
                            rs.getBoolean("checked")),
                    customerId, product.getId());
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public CartItem findCartIdById(Long cartId) {
        try {
            final String query = "SELECT cart_item.id, product.id, product.name, product.price, product.image_url, cart_item.quantity, cart_item.checked " +
                    "FROM cart_item INNER JOIN product ON product.id = cart_item.product_id " +
                    "WHERE cart_item.id = ?";
            return jdbcTemplate.queryForObject(query, cartRowMapper, cartId);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public void updateById(Long id, Long customerId, Long quantity, boolean checked) {
        final String sql = "UPDATE cart_item SET quantity = ?, checked = ? WHERE id = ? AND customer_id = ?";
        jdbcTemplate.update(sql, quantity, checked, id, customerId);
    }

    public void deleteCartItemById(Long id, Long customerId) {
        final String sql = "DELETE FROM cart_item WHERE id = ? AND customer_id = ?";
        jdbcTemplate.update(sql, id, customerId);
    }

    public void deleteAll(Long id) {
        final String sql = "DELETE FROM cart_item WHERE customer_id = ?";
        jdbcTemplate.update(sql, id);
    }
}
