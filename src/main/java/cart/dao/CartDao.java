package cart.dao;

import cart.domain.Cart;
import cart.dto.CartDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartDao {
    private final JdbcTemplate jdbcTemplate;

    RowMapper<Cart> rowMapper = (resultSet, rowNum) -> {
        Cart cart = new Cart(
                resultSet.getLong("id"),
                resultSet.getLong("member_id"),
                resultSet.getLong("product_id")
        );
        return cart;
    };

    public CartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Cart> findAll(Long memberId) {
        String sql = "select * from cart where member_id = ?";
        return jdbcTemplate.query(sql, rowMapper, memberId);
    }

    public void save(CartDto cartDto) {
        String sql = "insert into cart(member_id, product_id) values (?,?)";
        jdbcTemplate.update(sql, cartDto.getMemeberId(), cartDto.getProductId());
    }

    public void delete(Long productId) {
        String sql = "delete from cart where product_id = ?";
        jdbcTemplate.update(sql, productId);
    }

}
