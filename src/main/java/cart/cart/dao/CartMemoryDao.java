package cart.cart.dao;

import cart.cart.domain.Cart;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CartMemoryDao implements CartDao {
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final RowMapper<Cart> rowMapper;
    
    public CartMemoryDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.simpleJdbcInsert = initSimpleJdbcInsert(namedParameterJdbcTemplate);
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.rowMapper = initRowMapper();
    }
    
    private SimpleJdbcInsert initSimpleJdbcInsert(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName("cart")
                .usingGeneratedKeyColumns("id");
    }
    
    private RowMapper<Cart> initRowMapper() {
        return (rs, rowNum) -> new Cart(
                rs.getLong("id"),
                rs.getLong("member_id"),
                rs.getLong("product_id")
        );
    }
    
    @Override
    public List<Cart> findByMemberId(final Long memberId) {
        final String sql = "SELECT * FROM CART WHERE member_id=:member_id";
        final SqlParameterSource params = new MapSqlParameterSource("member_id", memberId);
        return namedParameterJdbcTemplate.query(sql, params, rowMapper);
    }
    
    @Override
    public void deleteByCartIdAndMemberId(final Long cartId, final Long memberId) {
        final String sql = "DELETE FROM CART WHERE id=:cart_id AND member_id=:member_id";
        final SqlParameterSource params = new MapSqlParameterSource()
                .addValue("cart_id", cartId)
                .addValue("member_id", memberId);
        namedParameterJdbcTemplate.update(sql, params);
    }
    
    @Override
    public void deleteByProductId(final Long productId) {
        final String sql = "DELETE FROM CART WHERE product_id=:product_id";
        final SqlParameterSource params = new MapSqlParameterSource()
                .addValue("product_id", productId);
        namedParameterJdbcTemplate.update(sql, params);
    }
    
    @Override
    public Long save(final Long productId, final Long memberId) {
        final SqlParameterSource params = new MapSqlParameterSource()
                .addValue("member_id", memberId)
                .addValue("product_id", productId);
        
        return simpleJdbcInsert.executeAndReturnKey(params).longValue();
    }
}
