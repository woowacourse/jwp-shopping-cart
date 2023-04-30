package cart.repository;

import cart.dto.cart.CartProductDto;
import cart.dto.product.ProductDto;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class CartDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart")
                .usingGeneratedKeyColumns("id");
    }

    public Long save(Long memberId, Long productId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("member_id", memberId);
        mapSqlParameterSource.addValue("product_id", productId);
        return simpleJdbcInsert.executeAndReturnKey(mapSqlParameterSource).longValue();
    }

    public List<CartProductDto> findAllCartProductByMemberId(Long memberId) {
        String sql = "SELECT C.id, P.id as p_id, P.name, P.price, P.image_url " +
                "FROM CART as C " +
                "JOIN MEMBER as M ON C.member_id = M.id " +
                "JOIN PRODUCT P ON C.product_id = P.id " +
                "WHERE C.member_id = ?";
        return jdbcTemplate.query(sql, cartProductRowMapper(), memberId);
    }

    private RowMapper<CartProductDto> cartProductRowMapper() {
        return (rs, rowNum) -> new CartProductDto(
                rs.getLong("id"),
                new ProductDto(rs.getLong("p_id"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getString("image_url")));
    }

    public void delete(Long memberId, Long cartId) {
        String sql = "DELETE FROM CART WHERE member_id = ? AND id = ?";
        jdbcTemplate.update(sql, memberId, cartId);
    }

    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM CART WHERE id = ? LIMIT 1";
        return jdbcTemplate.queryForObject(sql, Integer.class, id) > 0;
    }
}
