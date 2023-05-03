package cart.dao;

import cart.entity.CartItemEntity;
import cart.entity.ProductEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class JdbcCartItemDao implements CartItemDao {

    private final SimpleJdbcInsert simpleJdbcInsert;
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<CartItemEntity> cartItemMapper
            = (resultSet, rowNum) -> new CartItemEntity(
            resultSet.getInt("id"),
            resultSet.getInt("member_id"),
            resultSet.getInt("product_id")
    );

    private final RowMapper<ProductEntity> productMapper
            = (resultSet, rowNum) -> new ProductEntity(
            resultSet.getInt("id"),
            resultSet.getString("name"),
            resultSet.getInt("price"),
            resultSet.getString("image")
    );

    public JdbcCartItemDao(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("cartitem")
                .usingGeneratedKeyColumns("id");
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int addCartItem(CartItemEntity cartItemEntity) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(cartItemEntity);
        Number id = simpleJdbcInsert.executeAndReturnKey(parameterSource);
        return id.intValue();
    }

    @Override
    public List<ProductEntity> selectAllCartItems(int memberId) {
        String sql = "select product.id, product.name, product.price, product.image " +
                "from cartitem, product " +
                "where product.id = cartitem.product_id and cartitem.member_id = ?";
        return jdbcTemplate.query(sql, productMapper, memberId);
    }

    @Override
    public void removeCartItem(int cartItemId) {
        String sql = "delete from cartItem where id = ?";
        jdbcTemplate.update(sql, cartItemId);
    }
}
