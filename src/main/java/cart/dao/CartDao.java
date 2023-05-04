package cart.dao;

import cart.entity.Cart;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartDao implements Dao<Cart>{

    private final SimpleJdbcInsert simpleJdbcInsert;
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Cart> cartMapper
            = (resultSet, rowNum) -> new Cart(
            resultSet.getInt("id"),
            resultSet.getString("name"),
            resultSet.getInt("price"),
            resultSet.getString("image")
    );

    public CartDao(JdbcTemplate jdbcTemplate) {
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart")
                .usingGeneratedKeyColumns("id");
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insert(Cart cart) {
        return 0;
    }

    @Override
    public List<Cart> selectAll() {
        return null;
    }

    @Override
    public int update(Cart cart) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public List<Cart> selectById(Object email) {
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
