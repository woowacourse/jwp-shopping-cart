package cart.cart.dao;

import cart.cart.domain.Cart;
import cart.cart.dto.CartRequestDTO;
import cart.settings.exceptions.NotFoundException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class CartDAOImpl implements CartDAO {
    
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    
    private final RowMapper<Cart> rowMapper = (rs, rowNum) -> {
        final long id = rs.getLong("id");
        final long userId = rs.getLong("user_id");
        final long productId = rs.getLong("product_id");
        return Cart.of(id, userId, productId);
    };
    
    public CartDAOImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("carts")
                .usingGeneratedKeyColumns("id");
    }
    
    @Override
    public Cart create(final CartRequestDTO cartRequestDTO) {
        final MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("user_id", cartRequestDTO.getUserId())
                .addValue("product_id", cartRequestDTO.getProductId());
        final long id = this.simpleJdbcInsert.executeAndReturnKey(param).longValue();
        return Cart.of(id, cartRequestDTO.getUserId(), cartRequestDTO.getProductId());
    }
    
    @Override
    public Cart find(final CartRequestDTO cartRequestDTO) {
        final String sql = "select id, user_id, product_id from carts where user_id = ? and product_id = ?";
        final long userId = cartRequestDTO.getUserId();
        final long productId = cartRequestDTO.getProductId();
        try {
            return this.jdbcTemplate.queryForObject(sql, this.rowMapper, userId, productId);
        } catch (final Exception e) {
            throw new NotFoundException("해당 상품이 존재하지 않습니다.");
        }
    }
    
    @Override
    public List<Cart> findUserCart(final long userId) {
        final String sql = "select id, user_id, product_id from carts where user_id = ?";
        return this.jdbcTemplate.query(sql, this.rowMapper, userId);
    }
    
    @Override
    public void delete(final Cart cart) {
        final String sql = "delete from carts where id = ?";
        final long id = cart.getId();
        this.jdbcTemplate.update(sql, id);
    }
    
    @Override
    public void clear(final long userId) {
        final String sql = "delete from carts where user_id = ?";
        this.jdbcTemplate.update(sql, userId);
    }
}
