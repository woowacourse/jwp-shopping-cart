package cart.dao.cart;

import cart.dao.Dao;
import cart.dao.product.ProductEntity;
import java.util.Collections;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class CartDao implements Dao<CartEntity> {

    private final RowMapper<CartEntity> cartRowMapper = (rs, rowNum) ->
            new CartEntity(
                    rs.getLong("id"),
                    rs.getLong("member_id"),
                    rs.getLong("product_id")
            );
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CartDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public CartEntity findById(final Long id) {
        return null;
    }

    @Override
    public List<CartEntity> findAll() {
        final String sql = "SELECT * FROM cart";

        return jdbcTemplate.query(sql, cartRowMapper);
    }

    @Override
    public Long save(final CartEntity cartEntity) {
        return simpleJdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(cartEntity)).longValue();
    }

    @Override
    public int update(final CartEntity cartEntity) {
        return 0;
    }

    @Override
    public int deleteById(final Long id) {
        return 0;
    }

    public List<ProductEntity> findProductsByMemberId(final Long memberId) {
        final String sql = "SELECT * FROM cart INNER JOIN product ON cart.product_id = product.id "
                + "WHERE cart.member_id = ?";

        final RowMapper<ProductEntity> cartRowMapper = (rs, rowNum) ->
                new ProductEntity(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getString("image_url")
                );

        try {
            return jdbcTemplate.query(sql, cartRowMapper, memberId);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }
}
