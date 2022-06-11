package woowacourse.shoppingcart.cart.support.jdbc.dao;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import woowacourse.shoppingcart.cart.domain.Cart;

@Repository
public class CartItemDao {

    private static final RowMapper<Cart> ROW_MAPPER =
            (resultSet, rowNum) -> new Cart(
                    resultSet.getLong("id"),
                    resultSet.getLong("customer_id"),
                    resultSet.getLong("product_id"),
                    resultSet.getLong("quantity")
            );

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public CartItemDao(final DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("cart_item")
                .usingGeneratedKeyColumns("id");
    }

    public long addCartItem(final Cart cart) {
        final SqlParameterSource parameters = new BeanPropertySqlParameterSource(cart);
        return jdbcInsert.executeAndReturnKey(parameters)
                .longValue();
    }

    public boolean existsProductByCustomer(final long customerId, final long productId) {
        final String query = "SELECT EXISTS(SELECT id FROM cart_item WHERE customer_id=(:customerId) and product_id=(:productId)) as existable";
        final SqlParameterSource parameters = new MapSqlParameterSource("customerId", customerId)
                .addValue("productId", productId);
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, parameters,
                (resultSet, rowNum) -> resultSet.getBoolean("existable")));
    }

    public List<Cart> findAllByCustomerId(final long customerId) {
        final String sql = "SELECT id, customer_id, product_id, quantity FROM cart_item WHERE customer_id = (:customerId)";
        final SqlParameterSource parameters = new MapSqlParameterSource("customerId", customerId);
        return jdbcTemplate.query(sql, parameters, ROW_MAPPER);
    }

    public void deleteCartItem(final long id) {
        final String sql = "DELETE FROM cart_item WHERE id = (:id)";
        final SqlParameterSource parameters = new MapSqlParameterSource("id", id);
        jdbcTemplate.update(sql, parameters);
    }
}
