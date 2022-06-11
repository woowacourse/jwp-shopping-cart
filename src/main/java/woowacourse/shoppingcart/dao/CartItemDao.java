package woowacourse.shoppingcart.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Cart;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class CartItemDao {

    private static final RowMapper<Cart> CART_ROW_MAPPER = (rs, rowNum) -> new Cart(
            rs.getLong("id"),
            rs.getLong("customer_id"),
            rs.getLong("product_id"),
            rs.getInt("quantity")
    );

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CartItemDao(final DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("cart_item")
                .usingGeneratedKeyColumns("id");
    }

    public Long save(final Long customerId, final Long productId, final int quantity) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("customer_id", customerId)
                .addValue("product_id", productId)
                .addValue("quantity", quantity);
        return simpleJdbcInsert.executeAndReturnKey(sqlParameterSource).longValue();
    }

    public Optional<Cart> findById(long id) {
        String query = "SELECT id, customer_id, product_id, quantity FROM cart_item WHERE id=:id";
        SqlParameterSource nameParameters = new MapSqlParameterSource("id", id);

        try {
            Cart cart = template.queryForObject(query, nameParameters, CART_ROW_MAPPER);
            return Optional.ofNullable(cart);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }

    public Optional<List<Cart>> findAllByCustomerId(final Long customerId) {
        String query = "SELECT id, customer_id, product_id, quantity FROM cart_item WHERE customer_id=:customer_Id";
        SqlParameterSource nameParameters = new MapSqlParameterSource("customer_Id", customerId);

        try {
            List<Cart> carts = template.query(query, nameParameters, CART_ROW_MAPPER);
            return Optional.ofNullable(carts);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void updateQuantity(final Long customerId, final Long productId, final int quantity) {
        String query = "UPDATE cart_item SET quantity=:quantity WHERE customer_id=:customer_id AND product_id=:product_id";
        MapSqlParameterSource nameParameters = new MapSqlParameterSource("quantity", quantity)
                .addValue("customer_id", customerId)
                .addValue("product_id", productId);
        template.update(query, nameParameters);
    }

    public void deleteByCustomerIdAndProductId(final Long customerId, final Long productId) {
        String query = "DELETE FROM cart_item WHERE customer_id=:customer_id AND product_id=:product_id";
        MapSqlParameterSource nameParameters = new MapSqlParameterSource("customer_id", customerId)
                .addValue("product_id", productId);
        template.update(query, nameParameters);
    }

    public boolean existProductId(final Long productId) {
        String query = "SELECT EXISTS (SELECT * FROM cart_item WHERE product_id=:product_id)";

        MapSqlParameterSource nameParameters = new MapSqlParameterSource("product_id", productId);
        int count = template.queryForObject(query, nameParameters, Integer.class);
        return count != 0;
    }
}
