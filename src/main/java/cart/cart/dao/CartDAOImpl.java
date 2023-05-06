package cart.cart.dao;

import cart.cart.domain.Cart;
import cart.cart.dto.CartRequestDTO;
import cart.common.exceptions.NotFoundException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class CartDAOImpl implements CartDAO {
    
    public static final String USER_CART_NOT_FOUND_ERROR = "해당 유저의 장바구니가 없습니다.";
    public static final String CART_NOT_FOUND_ERROR = "해당 카트를 찾을 수 없습니다.";
    public static final String ITEM_NOT_FOUND_IN_CART_ERROR = "장바구니에 해당 상품이 없습니다.";
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
    public Cart find(final CartRequestDTO cartRequestDTO) throws NotFoundException {
        final String sql = "select id, user_id, product_id from carts where user_id = ? and product_id = ?";
        final long userId = cartRequestDTO.getUserId();
        final long productId = cartRequestDTO.getProductId();
        try {
            return this.jdbcTemplate.queryForObject(sql, this.rowMapper, userId, productId);
        } catch (final Exception e) {
            throw new NotFoundException(ITEM_NOT_FOUND_IN_CART_ERROR);
        }
    }
    
    @Override
    public List<Cart> findUserCart(final long userId) throws NotFoundException {
        final String sql = "select id, user_id, product_id from carts where user_id = ?";
        try {
            return this.jdbcTemplate.query(sql, this.rowMapper, userId);
        } catch (final Exception e) {
            throw new NotFoundException(USER_CART_NOT_FOUND_ERROR);
        }
    }
    
    @Override
    public void delete(final Cart cart) {
        final String sql = "delete from carts where id = ?";
        final long id = cart.getId();
        try {
            this.jdbcTemplate.update(sql, id);
        } catch (final Exception e) {
            throw new NotFoundException(CART_NOT_FOUND_ERROR);
        }
    }
    
    @Override
    public void clear(final long userId) {
        final String sql = "delete from carts where user_id = ?";
        try {
            this.jdbcTemplate.update(sql, userId);
        } catch (final Exception e) {
            throw new NotFoundException(USER_CART_NOT_FOUND_ERROR);
        }
    }
}