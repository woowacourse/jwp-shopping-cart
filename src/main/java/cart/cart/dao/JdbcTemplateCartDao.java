package cart.cart.dao;

import cart.cart.entity.CartEntity;
import cart.cart.entity.CartProductEntity;
import java.util.HashMap;
import java.util.List;
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
        parameters.put("member_id", memberId);
        parameters.put("product_id", productId);
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

    @Override
    public List<CartProductEntity> selectAllCartProductByMemberId(final int memberId) {
        String sql = "select c.id, p.name, p.price, p.image from cart c join products p\n"
                + "on c.product_id = p.id\n"
                + "where c.member_id = ?";

        return jdbcTemplate.query(sql, cartProductEntityRowMapper, memberId);
    }

    private final RowMapper<CartProductEntity> cartProductEntityRowMapper = (resultSet, rowNumber) -> {
        CartProductEntity cartProductEntity = new CartProductEntity(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getInt("price"),
                resultSet.getString("image"));
        return cartProductEntity;
    };
}
