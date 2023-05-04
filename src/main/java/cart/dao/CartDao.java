package cart.dao;

import cart.entity.Cart;
import cart.vo.Email;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartDao {

    RowMapper<Cart> cartRowMapper = (resultSet, rowNum) -> new Cart.Builder()
            .id(resultSet.getLong("id"))
            .email(Email.from(resultSet.getString("member_email")))
            .productId(resultSet.getLong("product_id"))
            .build();
    private final JdbcTemplate jdbcTemplate;

    public CartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Cart cart) {
        String sqlForSave = "INSERT INTO Cart(member_email, product_id) VALUES(?, ?)";

        jdbcTemplate.update(sqlForSave, cart.getEmail(), cart.getProductId());
    }

    public Cart findById(Long id) {
        String sqlForFindById = "SELECT * FROM Cart WHERE id = ?";
        return jdbcTemplate.queryForObject(sqlForFindById, cartRowMapper, id);
    }

    public List<Cart> selectAll(Email email) {
        String sqlForFindAll = "SELECT * FROM Cart WHERE member_email = ?";

        return jdbcTemplate.query(
                sqlForFindAll,
                cartRowMapper,
                email.getValue());
    }

    public void deleteById(Long id) {
        String sqlForDeleteById = "DELETE FROM Cart WHERE id = ?";
        jdbcTemplate.update(sqlForDeleteById, id);
    }

}
