package woowacourse.shoppingcart.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;

@Repository
public class CartItemDao {

    private static final RowMapper<CartItem> CART_ITEM_MAPPER = (rs, rowNum) ->
            new CartItem(
                    rs.getLong("id"),
                    rs.getLong("member_id"),
                    new Product(rs.getLong("p_id"), rs.getString("p_name"), rs.getInt("p_price"), rs.getInt("p_stock"),
                            rs.getString("p_image")),
                    rs.getInt("quantity")
            );

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CartItemDao(JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart_item")
                .usingGeneratedKeyColumns("id");
    }

    public Long save(CartItem cartItem) {
        MapSqlParameterSource parameters = new MapSqlParameterSource("member_id", cartItem.getMemberId())
                .addValue("product_id", cartItem.getProduct().getId())
                .addValue("quantity", cartItem.getQuantity());
        return simpleJdbcInsert.executeAndReturnKey(parameters)
                .longValue();
    }

    public List<CartItem> findByMemberId(Long memberId) {
        String sql = "SELECT c.id as id, c.member_id as member_id, c.product_id as product_id, c.quantity as quantity,"
                + "p.id as p_id, p.name as p_name, p.price as p_price, p.stock as p_stock, p.image_url as p_image "
                + "FROM cart_item AS c "
                + "INNER JOIN product AS p on c.product_id = p.id "
                + "WHERE c.member_id = :member_id";
        MapSqlParameterSource parameters = new MapSqlParameterSource("member_id", memberId);
        return namedParameterJdbcTemplate.query(sql, parameters, CART_ITEM_MAPPER);
    }

    public Optional<CartItem> findByMemberIdAndProductId(Long memberId, Long productId) {
        String sql = "SELECT c.id as id, c.member_id as member_id, c.product_id as product_id, c.quantity as quantity,"
                + "p.id as p_id, p.name as p_name, p.price as p_price, p.stock as p_stock, p.image_url as p_image "
                + "FROM cart_item AS c "
                + "INNER JOIN product AS p on c.product_id = p.id "
                + "WHERE c.member_id = :member_id AND c.product_id = :product_id";
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
        String sql = "DELETE FROM cart_item WHERE member_id = :member_id AND product_id = :product_id";
        MapSqlParameterSource parameters = new MapSqlParameterSource("member_id", memberId)
                .addValue("product_id", productId);
        namedParameterJdbcTemplate.update(sql, parameters);
    }
}
