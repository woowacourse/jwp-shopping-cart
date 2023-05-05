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
public class CartItemDao {

    private final SimpleJdbcInsert simpleJdbcInsert;
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<ProductEntity> productMapper
            = (resultSet, rowNum) -> new ProductEntity(
            resultSet.getInt("id"),
            resultSet.getString("name"),
            resultSet.getInt("price"),
            resultSet.getString("image")
    );

    private final RowMapper<Boolean> booleanMapper = (resultSet, rowNum) -> resultSet.getBoolean("isExist");

    public CartItemDao(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("cartitem")
                .usingGeneratedKeyColumns("id");
        this.jdbcTemplate = jdbcTemplate;
    }

    public int addCartItem(CartItemEntity cartItemEntity) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(cartItemEntity);
        Number id = simpleJdbcInsert.executeAndReturnKey(parameterSource);
        return id.intValue();
    }

    public boolean isCartItemExist(int memberId, int productId) {
        String sql = "select exists(" +
                "select * from cartItem where cartItem.member_id = ? and cartItem.product_id = ?) as isExist";
        return jdbcTemplate.queryForObject(sql, booleanMapper, memberId, productId);
    }

    public List<ProductEntity> selectAllCartItems(int memberId) {
        String sql = "select product.id, product.name, product.price, product.image " +
                "from cartitem, product " +
                "where product.id = cartitem.product_id and cartitem.member_id = ?";
        return jdbcTemplate.query(sql, productMapper, memberId);
    }

    public void deleteCartItem(int cartItemId) {
        String sql = "delete from cartItem where id = ?";
        jdbcTemplate.update(sql, cartItemId);
    }

    public void deleteAllCartItem() {
        String sql = "delete from cartItem";
        jdbcTemplate.update(sql);
    }

}
