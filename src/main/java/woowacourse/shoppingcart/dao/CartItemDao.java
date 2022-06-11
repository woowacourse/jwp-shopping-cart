package woowacourse.shoppingcart.dao;

import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.CartItem;

@Repository
public class CartItemDao {

    private static final RowMapper<CartItem> CART_ITEM_MAPPER = (rs, rowNum) ->
            new CartItem(
                    rs.getLong("id"),
                    rs.getLong("product_id"),
                    rs.getString("name"),
                    rs.getInt("price"),
                    rs.getInt("stock"),
                    rs.getString("image_url"),
                    rs.getInt("quantity")
            );

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CartItemDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    public void addCartItem(long memberId, long productId, int quantity) {
        String sql = "INSERT INTO CART_ITEM (member_id, product_id, quantity)"
                + "VALUES (:member_id, :product_id, :quantity)";
        SqlParameterSource params = new MapSqlParameterSource("member_id", memberId)
                .addValue("product_id", productId)
                .addValue("quantity", quantity);
        jdbcTemplate.update(sql, params);
    }

    public boolean isAlreadyInCart(long memberId, long productId) {
        String sql = "SELECT EXISTS "
                + "(SELECT 1 FROM CART_ITEM WHERE member_id = :member_id AND product_id = :product_id)";
        SqlParameterSource params = new MapSqlParameterSource("member_id", memberId)
                .addValue("product_id", productId);
        return jdbcTemplate.queryForObject(sql, params, Boolean.class);
    }

    public void increaseQuantity(long memberId, long productId, int increasingQuantity) {
        String sql = "UPDATE CART_ITEM SET quantity = quantity + :increasingQuantity "
                + "WHERE member_id = :member_id AND product_id = :product_id";
        SqlParameterSource params = new MapSqlParameterSource("increasingQuantity", increasingQuantity)
                .addValue("member_id", memberId)
                .addValue("product_id", productId);
        jdbcTemplate.update(sql, params);
    }

    public List<CartItem> findAll(long memberId) {
        String sql = "SELECT c.id, c.product_id, c.quantity, p.name, p.price, p.stock, p.image_url"
                + " FROM CART_ITEM c JOIN PRODUCT p ON c.product_id = p.id"
                + " WHERE member_id = :member_id";
        SqlParameterSource params = new MapSqlParameterSource("member_id", memberId);
        return jdbcTemplate.query(sql, params, CART_ITEM_MAPPER);
    }

    public void updateCartItemQuantity(long memberId, long productId, int updatingQuantity) {
        String sql = "UPDATE CART_ITEM SET quantity = :updatingQuantity "
                + "WHERE member_id = :member_id AND product_id = :product_id";
        SqlParameterSource params = new MapSqlParameterSource("updatingQuantity", updatingQuantity)
                .addValue("member_id", memberId)
                .addValue("product_id", productId);
        try {
            jdbcTemplate.update(sql, params);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        }
    }

    public void deleteCartItem(long memberId, long productId) {
        String sql = "DELETE FROM CART_ITEM WHERE member_id = :member_id AND product_id = :product_id";
        SqlParameterSource params = new MapSqlParameterSource("member_id", memberId)
                .addValue("product_id", productId);
        jdbcTemplate.update(sql, params);
    }
}
