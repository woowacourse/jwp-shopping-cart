package cart.dao.cart;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcCartDao implements CartDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCartDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(final Long memberId, final Long productId) {
        final String sql = "INSERT INTO cart_product(member_id, product_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, memberId, productId);
    }

    @Override
    public List<Long> findAllByMemberId(final Long memberId) {
        final String sql = "SELECT product_id FROM cart_product WHERE member_id = ?";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> resultSet.getLong(1), memberId);
    }
}
