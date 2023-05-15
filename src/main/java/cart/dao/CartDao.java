package cart.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import cart.domain.Product;

@Repository
public class CartDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<Product> cartItemRowMapper = (resultSet, rowNum) -> new Product(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getString("image_url"),
            resultSet.getInt("price")
    );

    public CartDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart")
                .usingGeneratedKeyColumns("id");
    }

    public List<Product> findCartItemsByMemberEmail(final String email) {
        String sql = "SELECT cart.id, product.name, product.image_url, product.price " +
                "FROM cart, product, member " +
                "WHERE product.id = cart.product_id " +
                "AND cart.member_id = member.id " +
                "AND member.email = ?";

        return jdbcTemplate.query(sql, cartItemRowMapper, email);
    }

    public Long saveCartItemByMemberEmail(final String email, final Long productId) {
        String selectMemberIdSql = "SELECT id FROM member WHERE email = ?";
        Long memberId = jdbcTemplate.queryForObject(selectMemberIdSql, Long.class, email);

        Map<String, Object> params = new HashMap<>();
        params.put("member_id", memberId);
        params.put("product_id", productId);

        return simpleJdbcInsert.executeAndReturnKey(params).longValue();
    }

    public void deleteCartItemById(final Long cartId) {
        String sql = "DELETE FROM cart WHERE id = ?";

        jdbcTemplate.update(sql, cartId);
    }
}
