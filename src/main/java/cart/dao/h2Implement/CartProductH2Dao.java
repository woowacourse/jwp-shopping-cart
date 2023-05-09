package cart.dao.h2Implement;

import cart.dao.CartProductDao;
import cart.dto.CartProductResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartProductH2Dao implements CartProductDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<CartProductResponse> cartMapper
            = (resultSet, rowNum) -> CartProductResponse.from(
            resultSet.getInt("id"),
            resultSet.getString("name"),
            resultSet.getInt("price"),
            resultSet.getString("image")
    );

    public CartProductH2Dao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<CartProductResponse> selectById(String email) {
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
