package cart.dao.user.userproduct;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

@Component
public class CartUserProductDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleInsert;

    public CartUserProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart_user_product")
                .usingGeneratedKeyColumns("cart_user_product_id");
    }

    public Long insert(CartUserProductEntity cartUserProductEntity) {
        Map<String, Long> params = Map.of(
                "cart_user_id", cartUserProductEntity.getCartUserId(),
                "product_id", cartUserProductEntity.getProductId()
        );

        return simpleInsert.executeAndReturnKey(params).longValue();
    }

    public List<CartUserProductEntity> findProductByCartUserId(Long id) {
        String findProductByCartUserIdQuery = "SELECT * FROM cart_user_product WHERE cart_user_id = ?";

        return jdbcTemplate.query(findProductByCartUserIdQuery,
                (rs, rowNum) -> toCartUserProductEntity(rs), id);
    }

    private CartUserProductEntity toCartUserProductEntity(ResultSet rs) throws SQLException {
        return new CartUserProductEntity(
                rs.getLong("cart_user_product_id"),
                rs.getLong("cart_user_id"),
                rs.getLong("product_id")
        );
    }

    public List<CartUserProductEntity> findAll() {
        String findProductByCartUserIdQuery = "SELECT * FROM cart_user_product";

        return jdbcTemplate.query(findProductByCartUserIdQuery, (rs, rowNum) -> toCartUserProductEntity(rs));
    }
}
