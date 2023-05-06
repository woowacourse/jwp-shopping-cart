package cart.dao;

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

    public long add(final long memberId, final long productId) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("member_id", memberId);
        sqlParameterSource.addValue("product_id", productId);

        return simpleJdbcInsert.executeAndReturnKey(sqlParameterSource).longValue();
    }

    public void deleteById(final long memberId, final long cartId) {
        String sql = "DELETE FROM cart WHERE id = ? AND member_id = ?";
        jdbcTemplate.update(sql, cartId, memberId);
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

    public Boolean existsById(final Long id) {
        String sql = "SELECT COUNT(*) FROM cart WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);

        if (count == null) {
            return false;
        }

        return count > 0;
    }
}
