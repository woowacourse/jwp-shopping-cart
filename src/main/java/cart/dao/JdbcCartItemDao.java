package cart.dao;

import cart.entity.CartItemEntity;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcCartItemDao implements CartItemDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCartItemDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long insert(final CartItemEntity cartItemEntity) {
        final String sql = "INSERT INTO cart_item(member_id, product_id) VALUES (?, ?)";
        try {
            return jdbcTemplate.update(sql, cartItemEntity.getMemberId(), cartItemEntity.getProductId());
        } catch (DataAccessException exception) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        }
    }
}
