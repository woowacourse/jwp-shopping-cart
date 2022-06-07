package woowacourse.shoppingcart.dao;

import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

@Repository
public class CartItemDao {

    private static final RowMapper<CartItem> CART_ITEM_MAPPER = (rs, rowNum) ->
            new CartItem(
                    rs.getLong("id"),
                    rs.getLong("member_id"),
                    rs.getLong("product_id"),
                    rs.getInt("quantity")
            );

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CartItemDao(final JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart_item")
                .usingGeneratedKeyColumns("id");
    }

    public Long findProductIdById(final Long cartId) {
        try {
            final String sql = "SELECT product_id FROM cart_item WHERE id = :id";
            MapSqlParameterSource parameters = new MapSqlParameterSource("id", cartId);
            return namedParameterJdbcTemplate.queryForObject(sql, parameters, (rs, rowNum) -> rs.getLong("product_id"));
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public Long save(CartItem cartItem) {
        BeanPropertySqlParameterSource parameters = new BeanPropertySqlParameterSource(cartItem);
        return simpleJdbcInsert.executeAndReturnKey(parameters)
                .longValue();
    }

    public List<CartItem> findByMemberId(Long memberId) {
        String sql = "SELECT id, member_id, product_id, quantity FROM cart_item WHERE member_id = :member_id";
        MapSqlParameterSource parameters = new MapSqlParameterSource("member_id", memberId);
        return namedParameterJdbcTemplate.query(sql, parameters, CART_ITEM_MAPPER);
    }

    public boolean exists(Long memberId, Long productId) {
        String sql = "SELECT EXISTS (SELECT 1 FROM cart_item WHERE member_id = :member_id AND product_id = :product_id)";
        MapSqlParameterSource parameters = new MapSqlParameterSource("member_id", memberId)
                .addValue("product_id", productId);
        return namedParameterJdbcTemplate.queryForObject(sql, parameters, Boolean.class);
    }

    public void updateQuantity(Long id, Integer quantity) {
        String sql = "UPDATE cart_item SET quantity = :quantity WHERE id = :id";
        MapSqlParameterSource parameters = new MapSqlParameterSource("id", id)
                .addValue("quantity", quantity);
        namedParameterJdbcTemplate.update(sql, parameters);
    }

    public void deleteById(final Long id) {
        final String sql = "DELETE FROM cart_item WHERE id = :id";
        MapSqlParameterSource parameters = new MapSqlParameterSource("id", id);
        namedParameterJdbcTemplate.update(sql, parameters);
    }
}
