package cart.dao.h2Implement;

import cart.dao.CartDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class CartH2Dao implements CartDao {

    private final SimpleJdbcInsert simpleJdbcInsert;
    private final JdbcTemplate jdbcTemplate;

    public CartH2Dao(JdbcTemplate jdbcTemplate) {
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart")
                .usingGeneratedKeyColumns("id");
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insert(int productId, String email) {
        String sql = "insert into cart (product_id, email) values (? , ?)";
        return jdbcTemplate.update(sql, productId, email);
    }

    @Override
    public int delete(int cartId, String email) {
        String sql = "delete from cart where id = ? and email = ? ";
        return jdbcTemplate.update(sql, cartId, email);
    }
}
