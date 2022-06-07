package woowacourse.shoppingcart.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

@Repository
public class CartItemDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<Cart> cartRowMapper = (rs, rowNum) ->
            new Cart(
                    rs.getLong("id"),
                    rs.getLong("product_id"),
                    rs.getString("name"),
                    rs.getInt("price"),
                    rs.getString("image_url"),
                    rs.getInt("quantity"),
                    rs.getBoolean("checked")
            );

    public CartItemDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("CART_ITEM")
                .usingGeneratedKeyColumns("id");
    }

    public Long addCartItem(Long customerId, Long productId, Integer quantity, Boolean checked) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("customer_id", customerId)
                .addValue("product_id", productId)
                .addValue("quantity", quantity)
                .addValue("checked", checked);
        return simpleJdbcInsert.executeAndReturnKey(params).longValue();
    }

    public List<Cart> findIByCustomerId(Long customerId) {
        final String sql =
                "SELECT c.id AS id, c.product_id AS product_id, p.name AS name, p.price AS price, p.image_url AS image_url, c.quantity AS quantity, c.checked AS checked "
                        + "FROM cart_item AS c "
                        + "JOIN product AS p ON c.product_id = p.id "
                        + "WHERE customer_id = ?";

        return jdbcTemplate.query(sql, cartRowMapper, customerId);
    }

    public Long findProductIdById(Long cartId) {
        try {
            final String sql = "SELECT product_id FROM cart_item WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getLong("product_id"), cartId);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public void deleteCartItem(Long id) {
        final String sql = "DELETE FROM cart_item WHERE id = ?";

        final int rowCount = jdbcTemplate.update(sql, id);
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }
}
