package cart.repository;

import cart.entity.CartItemEntity;
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

    public CartDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart")
                .usingGeneratedKeyColumns("id");
    }

    public long add(long memberId, long productId) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("member_id", memberId);
        sqlParameterSource.addValue("product_id", productId);

        return simpleJdbcInsert.executeAndReturnKey(sqlParameterSource).longValue();
    }

    public void deleteById(long cartId) {
        String sql = "DELETE FROM cart WHERE id = ?";
        jdbcTemplate.update(sql, cartId);
    }

    public List<CartItemEntity> findAllByMemberId(final long memberId) {
        String sql = "SELECT product.id, product.name, product.price, product.image_url"
                + " FROM product"
                + " JOIN cart"
                + " ON product.id = cart.product_id"
                + " WHERE cart.member_id = ?";
        return jdbcTemplate.query(sql, cartItemRowMapper(), memberId);
    }

    private RowMapper<CartItemEntity> cartItemRowMapper() {
        return (rs, rowNum) -> CartItemEntity.builder()
                .id(rs.getLong(1))
                .name(rs.getString(2))
                .price(rs.getInt(3))
                .imageUrl(rs.getString(4))
                .build();
    }
}
