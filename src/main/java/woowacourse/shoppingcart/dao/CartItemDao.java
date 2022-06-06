package woowacourse.shoppingcart.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class CartItemDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CartItemDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    public void addCartItem(long memberId, long productId, int quantity) {
        String sql = "INSERT INTO cart_item (member_id, product_id, quantity)"
                + "VALUES (:member_id, :product_id, :quantity)";
        SqlParameterSource params = new MapSqlParameterSource("member_id", memberId)
                .addValue("product_id", productId)
                .addValue("quantity", quantity);
        jdbcTemplate.update(sql, params);
    }


    public boolean isExistsMemberIdAndProductId(long memberId, long productId) {
        String sql = "SELECT EXISTS "
                + "(SELECT 1 FROM CART_ITEM WHERE member_id = :member_id AND product_id = :product_id)";
        SqlParameterSource params = new MapSqlParameterSource("member_id", memberId)
                .addValue("product_id", productId);
        return jdbcTemplate.queryForObject(sql, params, Boolean.class);
    }

    public void increaseQuantity(long memberId, long productId, int increasingQuantity) {
        String sql = "UPDATE CART_ITEM SET quantity = quantity + :increasingQuantity"
                + "WHERE member_id = :member_id AND product_id = :product_id";
        SqlParameterSource params = new MapSqlParameterSource("increasingQuantity", increasingQuantity)
                .addValue("member_id", memberId)
                .addValue("product_id", productId);
        jdbcTemplate.update(sql, params);
    }
}
