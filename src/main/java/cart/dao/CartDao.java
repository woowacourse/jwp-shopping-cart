package cart.dao;

import java.util.List;

import cart.entiy.CartEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CartDao {

    private final RowMapper<CartEntity> cartEntityRowMapper = (resultSet, rowNum) -> {
        String email = resultSet.getString("email");
        Long productId = resultSet.getLong("product_id");
        return new CartEntity(email, productId);
    };

    private final JdbcTemplate jdbcTemplate;

    public CartDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public CartEntity insert(final CartEntity cartEntity) {
        final String sql = "INSERT INTO CART (email, product_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, cartEntity.getEmail(), cartEntity.getProductId());
        return cartEntity;
    }

    public List<CartEntity> findByEmail(final String email) {
        final String sql = "SELECT email, product_id FROM CART WHERE email=?";
        return jdbcTemplate.query(sql, cartEntityRowMapper, email);
    }

    public void deleteById(final Long id) {
        final String sql = "DELETE FROM Cart WHERE cart_id=?";
        jdbcTemplate.update(sql, id);
    }
}
