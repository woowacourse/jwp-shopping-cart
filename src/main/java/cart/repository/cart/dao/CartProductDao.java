package cart.repository.cart.dao;

import cart.entiy.cart.CartEntityId;
import cart.entiy.cart.CartProductEntity;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class CartProductDao {

    private static final RowMapper<CartProductEntity> rowMapper = (rs, rowNum) -> new CartProductEntity(
            rs.getLong("cart_product_id"),
            rs.getLong("cart_id"),
            rs.getLong("product_id")
    );

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CartProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart_product")
                .usingGeneratedKeyColumns("cart_product_id");
    }

    public void insertAll(final List<CartProductEntity> cartProductEntities) {
        @SuppressWarnings("unchecked") final Map<String, Object>[] parameters = cartProductEntities.stream()
                .map(cartProductEntity -> Map.<String, Object>of(
                        "cart_id", cartProductEntity.getCartEntityId().getValue(),
                        "product_id", cartProductEntity.getProductEntityId().getValue()
                ))
                .toArray(Map[]::new);
        simpleJdbcInsert.executeBatch(parameters);
    }

    public void deleteAllByCartId(final CartEntityId cartId) {
        jdbcTemplate.update("DELETE FROM cart_product WHERE cart_id = ?", cartId.getValue());
    }

    public List<CartProductEntity> findAllByCartId(final CartEntityId cartId) {
        return jdbcTemplate.query("SELECT cart_product_id,cart_id,product_id FROM cart_product WHERE cart_id = ?",
                rowMapper,
                cartId.getValue()
        );
    }
}
