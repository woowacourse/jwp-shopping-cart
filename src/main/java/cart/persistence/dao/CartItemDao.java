package cart.persistence.dao;

import cart.persistence.entity.CartItemEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartItemDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CartItemDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertCartItems(List<CartItemEntity> entities) {
        String sql = "INSERT INTO cart_item(cart_id, product_id) VALUES(:cart_id, :product_id)";
        SqlParameterSource[] batchArgs = generateBatchArgs(entities);
        jdbcTemplate.batchUpdate(sql, batchArgs);
    }

    private SqlParameterSource[] generateBatchArgs(List<CartItemEntity> entities) {
        return entities.stream()
                .map(this::getMapSqlParameterSource)
                .toArray(SqlParameterSource[]::new);
    }

    private SqlParameterSource getMapSqlParameterSource(CartItemEntity entity) {
        return new MapSqlParameterSource("cart_id", entity.getCartId())
                .addValue("product_id", entity.getProductId());
    }

    public void deleteByCartId(Long cartId) {
        String sql = "DELETE FROM cart_item WHERE cart_id = :cart_id";

        var parameterSource = new MapSqlParameterSource("cart_id", cartId);

        jdbcTemplate.update(sql, parameterSource);
    }
}
