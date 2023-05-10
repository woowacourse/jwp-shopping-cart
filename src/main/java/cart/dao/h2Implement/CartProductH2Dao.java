package cart.dao.h2Implement;

import cart.dao.CartProductDao;
import cart.entity.Cart;
import cart.entity.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartProductH2Dao implements CartProductDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Cart> cartMapper
            = (resultSet, rowNum) -> new Cart(
            resultSet.getInt("id"),
            new Product(
                    resultSet.getString("name"),
                    resultSet.getInt("price"),
                    resultSet.getString("image")
            )
    );

    public CartProductH2Dao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Cart> selectById(String email) {
        String sql = "select C.id, name, price, image\n" +
                "from product P\n" +
                "join \n" +
                "(\n" +
                "  select product_id, id \n" +
                "  from cart\n" +
                "  where email = ? \n" +
                ") as C\n" +
                "on p.id = C.product_id";
        return jdbcTemplate.query(sql, cartMapper, email);
    }
}
