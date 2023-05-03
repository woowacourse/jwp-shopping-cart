package cart.dao;

import cart.entity.CartItemEntity;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcCartItemDao implements CartItemDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcCartItemDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long insert(final CartItemEntity cartItemEntity) {
        final String sql = "INSERT INTO cart_item(member_id, product_id) VALUES (?, ?)";
        return jdbcTemplate.update(sql, cartItemEntity.getMemberId(), cartItemEntity.getProductId());
    }
}
