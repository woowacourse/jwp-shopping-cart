package woowacourse.shoppingcart.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.CartItem;

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

    public Optional<CartItem> findByMemberIdAndProductId(Long memberId, Long productId) {
        String sql = "SELECT id, member_id, product_id, quantity FROM cart_item WHERE member_id = :member_id AND product_id = :product_id";
        MapSqlParameterSource parameters = new MapSqlParameterSource("member_id", memberId)
                .addValue("product_id", productId);
        return namedParameterJdbcTemplate.query(sql, parameters, CART_ITEM_MAPPER)
                .stream()
                .findAny();
    }

    public boolean exists(Long memberId, Long productId) {
        String sql = "SELECT EXISTS (SELECT 1 FROM cart_item WHERE member_id = :member_id AND product_id = :product_id)";
        MapSqlParameterSource parameters = new MapSqlParameterSource("member_id", memberId)
                .addValue("product_id", productId);
        return namedParameterJdbcTemplate.queryForObject(sql, parameters, Boolean.class);
    }

    public void updateQuantity(Long memberId, Long productId, Integer quantity) {
        String sql = "UPDATE cart_item SET quantity = :quantity WHERE member_id = :member_id AND product_id = :product_id";
        MapSqlParameterSource parameters = new MapSqlParameterSource("member_id", memberId)
                .addValue("product_id", productId)
                .addValue("quantity", quantity);
        namedParameterJdbcTemplate.update(sql, parameters);
    }

    public void deleteByMemberIdAndProductId(Long memberId, Long productId) {
        final String sql = "DELETE FROM cart_item WHERE member_id = :member_id AND product_id = :product_id";
        MapSqlParameterSource parameters = new MapSqlParameterSource("member_id", memberId)
                .addValue("product_id", productId);
        namedParameterJdbcTemplate.update(sql, parameters);
    }
}
