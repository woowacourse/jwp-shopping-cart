package cart.dao;

import cart.entity.Cart;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CartDao {

    private final JdbcTemplate jdbcTemplate;

    public CartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Cart cart) {
        String sqlForSave = "INSERT INTO Cart(member_email, product_id) VALUES(?, ?)";

        jdbcTemplate.update(sqlForSave, cart.getEmail(), cart.getProductId());
    }

}
