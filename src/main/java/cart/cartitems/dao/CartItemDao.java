package cart.cartitems.dao;

import cart.cartitems.dto.CartItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class CartItemDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public CartItemDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public CartItemDto saveItemOfMember(CartItemDto cartItemDto) {
        final SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate()).withTableName("cart_items");

        try {
            final Map<String, Long> params = Map.of("member_id", cartItemDto.getMemberId(), "product_id", cartItemDto.getProductId());
            simpleJdbcInsert.execute(params);

            return cartItemDto;
        } catch (DuplicateKeyException e) {
            throw new IllegalArgumentException("중복 키입니다");
        }
    }

    public List<Long> findProductIdsByMemberId(long memberId) {
        final String sql = "SELECT product_id FROM cart_items WHERE member_id = :memberId";
        final Map<String, Long> params = Map.of("memberId", memberId);

        return namedParameterJdbcTemplate.queryForList(sql, params, Long.class);
    }

    public int deleteItem(CartItemDto cartItemDto) {
        final String sql = "DELETE FROM cart_items WHERE member_id = :memberId AND product_id = :productId";
        final BeanPropertySqlParameterSource beanPropertySqlParameterSource = new BeanPropertySqlParameterSource(cartItemDto);

        return namedParameterJdbcTemplate.update(sql, beanPropertySqlParameterSource);
    }
}
