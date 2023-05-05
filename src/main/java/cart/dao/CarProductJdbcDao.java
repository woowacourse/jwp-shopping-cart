package cart.dao;

import cart.entity.CartProductEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CarProductJdbcDao implements CarProductDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insert;

    private final RowMapper<CartProductEntity> cartEntityRowMapper = ((rs, rowNum) ->
            new CartProductEntity(
                    rs.getLong("id"),
                    rs.getLong("productId"),
                    rs.getLong("memberId")
            ));

    public CarProductJdbcDao(final JdbcTemplate jdbcTemplate) {
        this.insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart_product")
                .usingGeneratedKeyColumns("id");
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(final CartProductEntity cart) {
        final Map<String, Object> parameters = new HashMap<>();

        parameters.put("memberId", cart.getMemberId());
        parameters.put("productId", cart.getProductId());

        insert.execute(parameters);
    }

    @Override
    public void deleteById(final CartProductEntity cart) {
        String sql = "delete from cart_product where memberId = ? and productId = ?";
        jdbcTemplate.update(sql, cart.getMemberId(), cart.getProductId());
    }

    @Override
    public List<CartProductEntity> findByMemberId(final Long memberId) {
        String sql = "select * from cart_product where memberId = ?";
        return jdbcTemplate.query(sql, cartEntityRowMapper, memberId);
    }
}
