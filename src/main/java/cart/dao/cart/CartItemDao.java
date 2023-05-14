package cart.dao.cart;

import cart.domain.cart.CartItem;
import cart.domain.product.Product;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

@Component
public class CartItemDao {

    private static final RowMapper<Product> productRowMapper = (rs, rowNum) -> new Product(
            rs.getLong("product_id"),
            rs.getString("name"),
            rs.getString("image_url"),
            rs.getBigDecimal("price")
    );

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CartItemDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert(Long memberId, CartItem cartItem) {
        String sql = "insert into CART_ITEM(member_id, product_id, amount) values (:member_id,:product_id,:new_amount) on duplicate key "
                + "update amount = amount + :new_amount";

        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("member_id", memberId)
                .addValue("product_id", cartItem.getProductId())
                .addValue("amount", cartItem.getAmount());

        jdbcTemplate.update(sql, paramSource);
    }

    public List<Product> findAll(Long memberId) {
        String sql = "select * from PRODUCT as p inner join (select product_id from CART_ITEM where member_id= :member_id) as c "
                + "on p.product_id = c.product_id";
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("member_id", memberId);

        return jdbcTemplate.query(sql, paramSource, productRowMapper);
    }

    public void delete(Long memberId, Long productId) {
        String sql = "delete from CART_ITEM where member_id=:member_id and product_id = :product_id";
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("member_id", memberId)
                .addValue("product_id", productId);

        jdbcTemplate.update(sql, paramSource);
    }
}
