package cart.repository.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CartDao {

    private final JdbcTemplate jdbcTemplate;

    public CartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long findByUserId(Long userId) {
        String sql = "SELECT id FROM CARTS WHERE user_id = ?";

        return jdbcTemplate.queryForObject(sql, (resultSet, rowNum) -> resultSet.getLong("id"), userId);
    }
}
