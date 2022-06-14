package woowacourse.shoppingcart.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.cart.Cart;
import woowacourse.shoppingcart.domain.cart.CartId;
import woowacourse.shoppingcart.domain.cart.Quantity;
import woowacourse.shoppingcart.domain.customer.CustomerId;
import woowacourse.shoppingcart.domain.product.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CartDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CartDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(final CustomerId customerId, final ProductId productId, final Quantity quantity) {
        final String sql = "insert into cart(customer_id, product_id, quantity) values (:customerId, :productId, :quantity)";
        final MapSqlParameterSource query = new MapSqlParameterSource();
        query.addValue("customerId", customerId.getValue());
        query.addValue("productId", productId.getValue());
        query.addValue("quantity", quantity.getValue());
        jdbcTemplate.update(sql, query);
    }

    public void deleteCarts(final CustomerId customerId, final List<ProductId> productIds) {
        final List<Integer> ids = productIds.stream()
                .map(ProductId::getValue)
                .collect(Collectors.toList());
        final String sql = "delete from cart where customer_id = :customerId and product_id in (:productIds)";
        final MapSqlParameterSource query = new MapSqlParameterSource();
        query.addValue("customerId", customerId.getValue());
        query.addValue("productIds", ids);

        jdbcTemplate.update(sql, query);
    }

    public List<Cart> getAllCartsBy(final CustomerId customerId) {
        final String sql = "select c.id id, p.id productId, p.name name, p.price price, p.thumbnail thumbnail, c.quantity quantity from cart c inner join product p on p.id = c.product_id where c.customer_id = :customerId";
        return jdbcTemplate.query(sql, new MapSqlParameterSource("customerId", customerId.getValue()), new CartMapper());
    }

    public boolean exists(final CustomerId customerId, final ProductId productId) {
        final String sql = "select exists(select customer_id, product_id from cart where customer_id = :customerId and product_id = :productId)";
        final MapSqlParameterSource query = new MapSqlParameterSource("customerId", customerId.getValue());
        query.addValue("productId", productId.getValue());
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, query, Boolean.class));
    }

    public void edit(final CustomerId customerId, final ProductId productId, final Quantity quantity) {
        final String sql = "update cart set quantity = :quantity where customer_id = :customerId and product_id = :productId";
        final MapSqlParameterSource query = new MapSqlParameterSource("customerId", customerId.getValue());
        query.addValue("productId", productId.getValue());
        query.addValue("quantity", quantity.getValue());
        jdbcTemplate.update(sql, query);
    }

    private static class CartMapper implements RowMapper<Cart> {
        @Override
        public Cart mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            return new Cart(new CartId(rs.getInt("id")),
                    new Product(
                            new ProductId(rs.getInt("productId")),
                            new Name(rs.getString("name")),
                            new Price(rs.getInt("price")),
                            new Thumbnail(rs.getString("thumbnail"))),
                    new Quantity(rs.getInt("quantity"))
            );
        }
    }
}
