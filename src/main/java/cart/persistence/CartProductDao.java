package cart.persistence;

import cart.business.domain.Cart;
import cart.business.domain.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Repository
public class CartProductDao {

    private final JdbcTemplate jdbcTemplate;

    public CartProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer insert(Cart cart, Product product) {
        String sql = "INSERT INTO CART_PRODUCT (product_id, cart_id) VALUES(?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setInt(1, product.getId());
            ps.setInt(2, cart.getId());

            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    public Integer remove(Product product) {
        final var query = "DELETE FROM CART_PRODUCT WHERE product_id = ?";
        return jdbcTemplate.update(query, product.getId());
    }
}
