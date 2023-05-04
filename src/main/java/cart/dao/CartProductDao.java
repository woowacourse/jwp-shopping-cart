package cart.dao;

import cart.domain.CartProduct;
import cart.domain.Product;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class CartProductDao {

    private final SimpleJdbcInsert insertActor;
    private final JdbcTemplate jdbcTemplate;

    private RowMapper<CartProduct> rowMapper = (resultSet, rowNum) -> new CartProduct(
            resultSet.getLong("id"),
            new Product(
                    resultSet.getLong("product_id"),
                    resultSet.getString("name"),
                    resultSet.getInt("price"),
                    resultSet.getString("image_url")));

    public CartProductDao(JdbcTemplate jdbcTemplate) {
        this.insertActor = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart_product")
                .usingGeneratedKeyColumns("id");
        this.jdbcTemplate = jdbcTemplate;
    }

    //TODO unique
    public Long save(Long memberId,
                     Long productId) {
        Map<String, Object> cartValues = new HashMap<>();
        cartValues.put("member_id", memberId);
        cartValues.put("product_id", productId);
        return insertActor.executeAndReturnKey(new MapSqlParameterSource(cartValues)).longValue();
    }

    public List<CartProduct> findAllByMemberId(Long memberId) {
        String sql = "SELECT c.id, c.product_id, p.name, p.price, p.image_url " +
                "FROM CART_PRODUCT c " +
                "join product p on c.product_id = p.id " +
                "where member_id = ?";
        return jdbcTemplate.query(sql, rowMapper, memberId);
    }

    public int deleteById(Long id) {
        String sql = "delete from cart_product where id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
