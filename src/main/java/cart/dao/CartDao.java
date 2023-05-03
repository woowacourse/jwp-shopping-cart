package cart.dao;

import cart.entity.ProductEntity;
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

    public List<ProductEntity> findByUserId(Long id) {
        String sql = "SELECT P.ID, PRICE, NAME, IMAGE_URL  FROM PRODUCT P, CART C WHERE P.ID = C.PRODUCT_ID AND C.USER_ID = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new ProductEntity(
                rs.getLong("ID"),
                rs.getString("NAME"),
                rs.getString("IMAGE_URL"),
                rs.getInt("PRICE"))
                , id
        );
    }
}
