package cart.cart.persistence;

import cart.product.persistence.ProductEntity;
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

    public CartUserProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart_user_product")
                .usingGeneratedKeyColumns("cart_user_product_id");
    }

    public Long insert(final CartUserProductEntity cartUserProductEntity) {
        final Map<String, Long> params = Map.of(
                "cart_user_id", cartUserProductEntity.getCartUserId(),
                "product_id", cartUserProductEntity.getProductId()
        );

        return simpleInsert.executeAndReturnKey(params).longValue();
    }

    public List<ProductEntity> findProductByCartUserId(final Long cartUserId) {
        final String findProductByCartUserIdQuery
                = "SELECT * FROM cart_user_product "
                + "INNER JOIN product "
                + "ON cart_user_product.product_id = product.product_id "
                + "WHERE cart_user_id = ?";

        return jdbcTemplate.query(findProductByCartUserIdQuery, (rs, rowNum) -> toProductEntity(rs), cartUserId);
    }

    private ProductEntity toProductEntity(final ResultSet resultSet) throws SQLException {
        return new ProductEntity(
                resultSet.getLong("product_id"),
                resultSet.getString("name"),
                resultSet.getInt("price"),
                resultSet.getString("category"),
                resultSet.getString("image_url")
        );
    }

    private CartUserProductEntity toCartUserProductEntity(final ResultSet rs) throws SQLException {
        return new CartUserProductEntity(
                rs.getLong("cart_user_product_id"),
                rs.getLong("cart_user_id"),
                rs.getLong("product_id")
        );
    }

    public List<CartUserProductEntity> findAll() {
        final String findProductByCartUserIdQuery = "SELECT * FROM cart_user_product";

        return jdbcTemplate.query(findProductByCartUserIdQuery, (rs, rowNum) -> toCartUserProductEntity(rs));
    }

    public int deleteByCartUserIdAndProductId(final Long cartUserId, final Long productId) {
        final String deleteByCartUserIdAndProductIdQuery =
                "DELETE FROM cart_user_product WHERE cart_user_id = ? AND product_id = ?";

        return jdbcTemplate.update(con -> {
            final PreparedStatement preparedStatement = con.prepareStatement(deleteByCartUserIdAndProductIdQuery);

            preparedStatement.setLong(1, cartUserId);
            preparedStatement.setLong(2, productId);
            return preparedStatement;
        });
    }
}
