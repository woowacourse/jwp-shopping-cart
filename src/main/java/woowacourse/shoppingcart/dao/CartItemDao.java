package woowacourse.shoppingcart.dao;

import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

import java.util.List;

@Repository
public class CartItemDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final NamedParameterJdbcTemplate template;

    private static final RowMapper<CartItem> CART_ROW_MAPPER = (rs, rowNum) -> new CartItem(
            rs.getLong("product_id"),
            rs.getString("product_name"),
            rs.getInt("product_price"),
            rs.getInt("quantity"),
            rs.getString("product_image_url")
    );

    public CartItemDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("cart_item")
                .usingGeneratedKeyColumns("id");
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

    public Long saveCartItem(final Long customerId, final Long productId) {
        SqlParameterSource sqlParameter = new MapSqlParameterSource()
                .addValue("customer_id", customerId)
                .addValue("product_id", productId)
                .addValue("quantity", 1);

        return simpleJdbcInsert.executeAndReturnKey(sqlParameter).longValue();
    }

    public List<CartItem> findAllByCustomerId(Long customerId) {
        String query = "SELECT p.id AS product_id, p.name AS product_name, p.price AS product_price, ca.quantity, p.image_url AS product_image_url"
                + " FROM cart_item AS ca"
                + " LEFT JOIN product as p ON p.id = ca.product_id"
                + " WHERE ca.customer_id = :customerId";
        SqlParameterSource nameParameters = new MapSqlParameterSource("customerId", customerId);

        return template.query(query, nameParameters, CART_ROW_MAPPER);
    }

    public void updateQuantity(Long customerId, Long productId, int quantity) {
        String query = "UPDATE cart_item SET quantity = :quantity WHERE customer_id = :customer_id AND product_id = :product_id";
        MapSqlParameterSource nameParameters = new MapSqlParameterSource("quantity", quantity)
                .addValue("customer_id", customerId)
                .addValue("product_id", productId);

        template.update(query, nameParameters);
    }

    public void deleteCartItem(final Long id) {
        final String sql = "DELETE FROM cart_item WHERE id = ?";

        final int rowCount = jdbcTemplate.update(sql, id);
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }
}
