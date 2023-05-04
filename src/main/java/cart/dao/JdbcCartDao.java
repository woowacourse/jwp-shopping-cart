package cart.dao;

import cart.entity.Item;
import cart.entity.PutCart;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcCartDao implements CartDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(PutCart putCart) {
        String sql = "insert into cart(member_id, item_id) values(?, ?)";

        jdbcTemplate.update(sql, putCart.getMemberId(), putCart.getProductId());
    }
}
