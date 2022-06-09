package woowacourse.shoppingcart.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.cart.Cart;
import woowacourse.shoppingcart.exception.ExceptionMessage;
import woowacourse.shoppingcart.exception.ProductNotFoundException;

@Repository
public class CartDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public CartDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("cart")
                .usingGeneratedKeyColumns("id");
    }

    public Cart save(Cart cart) {
        long id = jdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(cart))
                .longValue();
        return new Cart(id, cart.getProductId(), cart.getCustomerId(), cart.getQuantity());
    }

    public Cart update(Cart cart) {
        String sql = "UPDATE cart SET quantity = :quantity WHERE customer_id = :customerId AND product_id = :productId";
        SqlParameterSource parameters = new MapSqlParameterSource(
                Map.of("customerId", cart.getCustomerId(), "productId", cart.getProductId(), "quantity",
                        cart.getQuantity()));
        if (jdbcTemplate.update(sql, parameters) == 0) {
            throw new ProductNotFoundException(ExceptionMessage.CODE_3001.getMessage());
        }
        return cart;
    }

    public Optional<Cart> findByCustomerIdAndProductId(Long customerId, Long productId) {
        String sql = "select id, product_id, customer_id, quantity from cart "
                + "where customer_id = :customerId AND product_id = :productId";
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(sql, Map.of("customerId", customerId, "productId", productId),
                            getCartRowMapper())
            );
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    public List<Cart> findByCustomerId(Long customerId) {
        String sql = "select id, product_id, customer_id, quantity from cart where customer_id = :customerId";
        return jdbcTemplate.query(sql, Map.of("customerId", customerId), getCartRowMapper());
    }

    public int deleteByCustomerIdAndCartIds(Long customerId, List<Long> productIds) {
        String sql = "DELETE FROM cart WHERE customer_id = :customerId AND product_id IN (:productIds)";
        return jdbcTemplate.update(sql, Map.of("customerId", customerId, "productIds", productIds));
    }

    private RowMapper<Cart> getCartRowMapper() {
        return (rs, rowNum) -> new Cart(
                rs.getLong("id"),
                rs.getLong("product_id"),
                rs.getLong("customer_id"),
                rs.getInt("quantity")
        );
    }
}
