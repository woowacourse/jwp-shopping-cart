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
import java.util.Optional;

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
            new Product(
                    resultSet.getLong("product_id"),
                    resultSet.getString("name"),
                    resultSet.getString("image_url"),
                    resultSet.getInt("price")
            );

    private final RowMapper<Long> deleteCartItemRowMapper = (resultSet, rowNum) ->
            resultSet.getLong("id");

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
    public void deleteCartItem(Long cartId) {
        String sql = "DELETE FROM cart WHERE id = ?";
        jdbcTemplate.update(sql, cartId);
    }

    @Override
    public Optional<Long> findOneCartItem(Member member, Long productId) {
        String sql = "SELECT * FROM cart WHERE product_id = ? AND member_id = ?";
        return jdbcTemplate.query(sql, deleteCartItemRowMapper, productId, member.getId()).stream()
                .findAny();
    }
}
