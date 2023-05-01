package cart.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartInProductDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private final RowMapper<CartInProductEntity> rowMapper = (rs, rowNum) ->
            new CartInProductEntity(
                    rs.getLong("id"),
                    rs.getLong("cart_id"),
                    rs.getLong("product_id")
            );

    public CartInProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("CART_IN_PRODUCT")
                .usingGeneratedKeyColumns("id");
    }

    public void save(final CartInProductEntity cartInProductEntity) {
        simpleJdbcInsert.execute(new BeanPropertySqlParameterSource(cartInProductEntity));
    }

    public List<CartInProductEntity> findProductsByCart(final CartEntity cartEntity) {
        final String sql = "SELECT * FROM CART_IN_PRODUCT CIP WHERE CIP.cart_id = ?";

        return jdbcTemplate.query(sql, rowMapper, cartEntity.getId());
    }

    public void deleteProductByCartAndProductId(final CartEntity cartEntity, final Long productId) {
        final String sql = "DELETE FROM CART_IN_PRODUCT CIP WHERE CIP.cart_id = ? and CIP.product_id = ?";

        jdbcTemplate.update(sql, cartEntity.getId(), productId);
    }
}
