package cart.dao;

import cart.domain.Product;
import cart.domain.cart.Cart;
import cart.domain.cart.CartDao;
import cart.domain.member.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class H2CartDao implements CartDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public H2CartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Product> rowMapper = (resultSet, rowNum) ->
            new Product(resultSet.getString("name"), resultSet.getString("image_url"), resultSet.getInt("price"));

    @Override
    public Long addProduct(Cart cart) {
        BeanPropertySqlParameterSource parameters = new BeanPropertySqlParameterSource(cart);
        return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    @Override
    public List<Product> findProductsByUserId(Long memberId) {
        String sql = "SELECT * FROM cart c JOIN product p ON c.product_id = p.id WHERE c.member_id = ?";
        return jdbcTemplate.query(sql, rowMapper, memberId);
    }

    @Override
    public int deleteCartItem(Member member, Long productId) {
        String sql = "DELETE FROM cart WHERE product_id = ? AND member_id = ?";
        return jdbcTemplate.update(sql, productId, member.getId());
    }
}
