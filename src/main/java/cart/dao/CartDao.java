package cart.dao;

import cart.entity.Cart;
import cart.vo.Email;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public List<Cart> selectAll(String email) {
        String sqlForFindAll = "SELECT * FROM Cart WHERE member_email = ?";

        return jdbcTemplate.query(
                sqlForFindAll,
                (resultSet, rowNum) -> new Cart.Builder()
                        .id(resultSet.getLong("id"))
                        .email(Email.from(resultSet.getString("member_email")))
                        .productId(resultSet.getLong("product_id"))
                        .build(),
                email);
    }

}
