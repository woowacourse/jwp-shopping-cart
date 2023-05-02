package cart.cart.dao;

import cart.cart.entity.CartEntity;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcTemplateCartDao implements CartDao{

    private final SimpleJdbcInsert insertCart;
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateCartDao(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);

        insertCart = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart")
                .usingGeneratedKeyColumns("id");
    }


    @Override
    public CartEntity insert(final int memberId, final int productId) {
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("memberId", memberId);
        parameters.put("productId", productId);
        int id = insertCart.executeAndReturnKey(parameters).intValue();

        return findById(id);
    }

    @Override
    public CartEntity findById(final int cartId) {
        String sql = "select * from cart where id = ?";

        return jdbcTemplate.queryForObject(sql, cartEntityRowMapper, cartId);
    }

    private final RowMapper<CartEntity> cartEntityRowMapper = (resultSet, rowNumber) -> {
        CartEntity cartEntity = new CartEntity(
                resultSet.getInt("id"),
                resultSet.getInt("member_id"),
                resultSet.getInt("product_id"));
        return cartEntity;
    };
}
