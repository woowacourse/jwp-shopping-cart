package cart.dao;

import cart.entity.Cart;
import cart.entity.CartProduct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartDao {

    private final SimpleJdbcInsert simpleJdbcInsert;
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<CartProduct> cartMapper
            = (resultSet, rowNum) -> new CartProduct(
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

    public int insert(Cart cart) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(cart);
        Number id = simpleJdbcInsert.executeAndReturnKey(parameterSource);
        return id.intValue();
    }

    public List<CartProduct> selectAll() {
        return null;
    }

    public int update(CartProduct cartProduct) {
        return 0;
    }

    public int delete(int cartId, String email) {
        String sql = "delete from cart where id = ? and email = ? ";
        return jdbcTemplate.update(sql, cartId, email);
    }

    public List<CartProduct> selectById(String email) {
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
