package cart.dao.user.userproduct;

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

}
