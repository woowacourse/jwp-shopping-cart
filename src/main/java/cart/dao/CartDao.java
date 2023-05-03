package cart.dao;

import cart.entity.CartEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartDao {

    private final JdbcTemplate jdbcTemplate;

    public CartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(final Long memberId, final Long productId) {
        String sql = "INSERT INTO CART (member_id, product_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, memberId, productId);
    }

    public List<CartEntity> findAll() {
        String sql = "SELECT * FROM CART";
        return jdbcTemplate.query(sql, (rs, rowNum)
                        -> new CartEntity(
                        rs.getLong("id"),
                        rs.getLong("member_id"),
                        rs.getLong("product_id")
                )
        );
    }

}
