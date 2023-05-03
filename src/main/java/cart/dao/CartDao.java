package cart.dao;

import cart.dao.dto.CartProductDto;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class CartDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CartDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart")
                .usingGeneratedKeyColumns("id");
    }

    public long insert(final long customerId, final long productId) {
        Number key = simpleJdbcInsert.executeAndReturnKey(
                Map.of("customer_id", customerId, "product_id", productId));
        return key.longValue();
    }

    public List<CartProductDto> findAllCartProductByCustomerId(final long customerId) {
        String sql = "SELECT cart.id AS cart_id, product.name, product.img_url, product.price "
                + "from cart INNER JOIN product ON cart.product_id = product.id "
                + "WHERE cart.customer_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new CartProductDto.Builder()
                .id(rs.getLong("cart_id"))
                .price(rs.getInt("price"))
                .productName(rs.getString("name"))
                .imgUrl(rs.getString("img_url"))
                .build(), customerId);
    }

    public void deleteById(final long cartId) {
        String sql = "DELETE FROM cart WHERE id = ?";
        jdbcTemplate.update(sql, cartId);
    }

}
