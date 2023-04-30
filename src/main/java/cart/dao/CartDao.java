package cart.dao;

import cart.entity.CartEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartDao {

    private final JdbcTemplate jdbcTemplate;

    public CartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CartEntity> findAllByMemberId(final Long memberId) {
        final String sql = "SELECT id, member_id, product_id FROM cart WHERE memberId = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new CartEntity(
                rs.getLong("id"),
                rs.getLong("member_id"),
                rs.getLong("product_id")
        ), memberId);
    }
}
