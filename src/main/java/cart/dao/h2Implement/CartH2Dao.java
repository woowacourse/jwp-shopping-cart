package cart.dao.h2Implement;

import cart.dao.CartDao;
import cart.entity.Cart;
import cart.entity.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    private final RowMapper<Cart> cartMapper
            = (resultSet, rowNum) -> new Cart(
            resultSet.getInt("id"),
            new Product(
                    resultSet.getString("name"),
                    resultSet.getInt("price"),
                    resultSet.getString("image")
            )
    );

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
