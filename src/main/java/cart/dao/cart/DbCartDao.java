package cart.dao.cart;

import cart.domain.Cart;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DbCartDao implements CartDao {
    private final JdbcTemplate jdbcTemplate;

    RowMapper<Cart> rowMapper = (resultSet, rowNum) -> {
        Cart cart = new Cart(
                resultSet.getLong("id"),
                resultSet.getLong("member_id"),
                resultSet.getLong("product_id")
        );
        return cart;
    };

    public DbCartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Cart> findAll(Long memberId) {
        String sql = "select * from cart where member_id = ?";
        return jdbcTemplate.query(sql, rowMapper, memberId);
    }

    @Override
    public void save(Long memberId, Long productId) {
        String sql = "insert into cart(member_id, product_id) values (?,?)";
        jdbcTemplate.update(sql, memberId, productId);
    }

    @Override
    public void delete(Long memberId, Long productId) {
        String sql = "delete from cart where member_id = ? and product_id = ?";
        jdbcTemplate.update(sql, memberId, productId);
    }

}
