package cart.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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

}
