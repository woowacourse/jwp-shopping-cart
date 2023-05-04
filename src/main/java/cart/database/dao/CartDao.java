package cart.database.dao;

import cart.entity.CartItemEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartDao {

    private static final int DEFAULT_COUNT = 0;

    private final JdbcTemplate jdbcTemplate;

    public CartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(Long userId, Long productId) {
        String sql = "INSERT INTO CART (USER_ID, PRODUCT_ID, COUNT) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, userId, productId, DEFAULT_COUNT);
    }

    public List<CartItemEntity> findByUserId(Long id) {
        String sql = "SELECT *  FROM CART WHERE USER_ID = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new CartItemEntity(
                        rs.getLong("ID"),
                        rs.getLong("USER_ID"),
                        rs.getLong("PRODUCT_ID"),
                        rs.getInt("COUNT"))
                , id
        );
    }

    public void deleteByUserIdAndCartId(Long userId, Long cartId) {
        String sql = "DELETE FROM CART C WHERE C.USER_ID=? AND ID=?";
        jdbcTemplate.update(sql, userId, cartId);
    }
}
