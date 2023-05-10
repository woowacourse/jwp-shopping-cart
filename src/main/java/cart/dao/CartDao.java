package cart.dao;

import java.util.List;

import cart.entity.CartEntity;
import cart.entity.CartProductJoinEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class CartDao {

    private final JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert simpleJdbcInsert;


    public CartDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("CART").usingGeneratedKeyColumns("cart_id");
    }

    public long insert(CartEntity cartEntity) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("product_id", cartEntity.getProductId())
                .addValue("member_id", cartEntity.getMemberId());
        return simpleJdbcInsert.executeAndReturnKey(params).longValue();
    }

    public List<CartProductJoinEntity> selectAllProductByMemberId(Long memberId) {
        String sql = "SELECT * FROM (SELECT CART.product_id, cart_id FROM CART WHERE member_id = ?) AS CP "
                + "INNER JOIN PRODUCT ON CP.product_id = PRODUCT.product_id ";
        return jdbcTemplate.query(sql, cartProductJoinEntityRowMapper(), memberId);
    }

    private RowMapper<CartProductJoinEntity> cartProductJoinEntityRowMapper() {
        return (rs, rowNum) ->
                new CartProductJoinEntity.Builder()
                        .cartId(rs.getLong("cart_id"))
                        .productId(rs.getLong("product_id"))
                        .productName(rs.getString("name"))
                        .productImgUrl(rs.getString("img_url"))
                        .productPrice(rs.getInt("price"))
                        .build();
    }

    public Boolean isNotExistByCartId(Long cartId) {
        String sql = "SELECT EXISTS(SELECT 1 FROM CART WHERE cart_id = ?)";
        return Boolean.FALSE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, cartId));
    }

    public void deleteByCartId(Long cartId) {
        String sql = "DELETE FROM CART WHERE cart_id = ?";
        jdbcTemplate.update(sql, cartId);
    }
}
