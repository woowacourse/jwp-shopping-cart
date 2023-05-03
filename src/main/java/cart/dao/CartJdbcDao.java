package cart.dao;

import cart.entity.CartEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CartJdbcDao implements CartDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insert;

    private final RowMapper<CartEntity> cartEntityRowMapper = ((rs, rowNum) ->
            new CartEntity(
                    rs.getLong("id"),
                    rs.getLong("productId"),
                    rs.getLong("memberId")
            ));

    public CartJdbcDao(final JdbcTemplate jdbcTemplate) {
        this.insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart")
                .usingGeneratedKeyColumns("id");
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(final CartEntity cart) {
        final Map<String, Object> parameters = new HashMap<>();

        parameters.put("memberId", cart.getMemberId());
        parameters.put("productId", cart.getProductId());

        insert.execute(parameters);
    }

    @Override
    public void deleteById(final Long memberId, final Long productId) {
        String sql = "delete from cart where memberId = ? and productId = ?";
        jdbcTemplate.update(sql, memberId, productId);
    }

    @Override
    public List<CartEntity> findByMemberId(final Long memberId) {
        String sql = "select * from cart where memberId = ?";
        return jdbcTemplate.query(sql, cartEntityRowMapper, memberId);
    }
}
