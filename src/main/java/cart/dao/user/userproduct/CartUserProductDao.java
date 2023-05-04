package cart.dao.user.userproduct;

import java.sql.PreparedStatement;
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

    public List<CartUserProductEntity> findProductByCartUserId(Long cartUserId) {
        String findProductByCartUserIdQuery = "SELECT * FROM cart_user_product WHERE cart_user_id = ?";

        return jdbcTemplate.query(findProductByCartUserIdQuery,
                (rs, rowNum) -> toCartUserProductEntity(rs), cartUserId);
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

    public void deleteByCartUserIdAndProductId(Long cartUserId, Long productId) {
        String deleteByCartUserIdAndProductIdQuery =
                "DELETE FROM cart_user_product WHERE cart_user_id = ? AND product_id = ?";

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(deleteByCartUserIdAndProductIdQuery);

            preparedStatement.setLong(1, cartUserId);
            preparedStatement.setLong(2, productId);
            return preparedStatement;
        });
    }
}
