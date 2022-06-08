package woowacourse.shoppingcart.dao;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.dto.cartitem.CartResponse;

@Repository
@RequiredArgsConstructor
public class CartQueryDao {

    private static final String FIND_ALL_CART_BY_CUSTOMER_ID_SQL
            = "select ci.product_id, p.image_url, p.name , p.price , p.quantity , ci.count "
            + "from product p "
            + "inner join cart_item ci on p.id = ci.product_id "
            + "inner join customer c on c.id = ci.customer_id "
            + "where ci.customer_id = ?";

    private static final RowMapper<CartResponse> ROW_MAPPER = (rs, rn) -> {
        long productId = rs.getLong("product_id");
        String thumbnailUrl = rs.getString("image_url");
        String name = rs.getString("name");
        int price = rs.getInt("price");
        long quantity = rs.getLong("quantity");
        long count = rs.getLong("count");

        return new CartResponse(productId, thumbnailUrl, name, price, quantity, count);
    };

    private final JdbcTemplate jdbcTemplate;

    public List<CartResponse> findAllCartByCustomerId(long customerId) {
        return jdbcTemplate
                .query(FIND_ALL_CART_BY_CUSTOMER_ID_SQL, ROW_MAPPER, customerId);
    }
}
