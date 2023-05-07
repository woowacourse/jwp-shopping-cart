package cart.dao;

import cart.dto.entity.CartEntity;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CartDao {
    private final JdbcTemplate jdbcTemplate;

    public CartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CartEntity> findByMemberId(int memberId) {
        final String sql = "SELECT * FROM cart WHERE member_id = ?";
        BeanPropertyRowMapper<CartEntity> mapper = BeanPropertyRowMapper.newInstance(CartEntity.class);
        return jdbcTemplate.query(sql, mapper, memberId);
    }
}
