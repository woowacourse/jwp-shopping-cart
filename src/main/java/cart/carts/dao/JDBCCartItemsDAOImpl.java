package cart.carts.dao;

import cart.carts.domain.CartItem;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class JDBCCartItemsDAOImpl implements CartItemsDAO {
    
    private static final String SELECT_CART_ITEM_BY_ID = "SELECT * FROM cart_items WHERE id = ?";
    private static final String SELECT_ALL_CART_ITEMS = "SELECT * FROM cart_items";
    private static final String DELETE_CART_ITEM = "DELETE FROM cart_items WHERE id = ?";
    private final RowMapper<CartItem> rowMapper = (rs, rowNum) -> {
        final long id = rs.getLong("id");
        final long productId = rs.getLong("product_id");
        return new CartItem(id, productId);
    };
    
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    
    public JDBCCartItemsDAOImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart_items")
                .usingGeneratedKeyColumns("id");
    }
    
    @Override
    public CartItem create(final long productId) {
        final SqlParameterSource parameter = new MapSqlParameterSource()
                .addValue("product_id", productId);
        final long cartItemId = this.simpleJdbcInsert.executeAndReturnKey(parameter).longValue();
        return new CartItem(cartItemId, productId);
    }
    
    @Override
    public void delete(final CartItem item) {
        this.jdbcTemplate.update(DELETE_CART_ITEM, item.getId());
    }
    
    @Override
    public CartItem findById(final long cartId) {
        return this.jdbcTemplate.queryForObject(SELECT_CART_ITEM_BY_ID, this.rowMapper, cartId);
    }
    
    @Override
    public List<CartItem> findAll() {
        return this.jdbcTemplate.query(SELECT_ALL_CART_ITEMS, this.rowMapper);
    }
}
