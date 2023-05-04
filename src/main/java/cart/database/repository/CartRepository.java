package cart.database.repository;

import cart.controller.dto.response.CartItemResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartRepository {

    private final JdbcTemplate jdbcTemplate;

    public CartRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CartItemResponse> findCartsWithProductByUserId(Long userId) {
        String sql = "SELECT C.ID CART_ID, PRODUCT_ID, NAME, PRICE, IMAGE_URL, COUNT FROM CART C, PRODUCT P" +
                " WHERE C.PRODUCT_ID=P.ID AND C.USER_ID=?";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                        new CartItemResponse(
                                rs.getLong("CART_ID"),
                                rs.getLong("PRODUCT_ID"),
                                rs.getString("NAME"),
                                rs.getString("IMAGE_URL"),
                                rs.getInt("PRICE"),
                                rs.getInt("COUNT"))
                , userId);
    }
}
