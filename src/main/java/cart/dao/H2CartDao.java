package cart.dao;

import cart.entity.CartEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class H2CartDao implements CartDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public H2CartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart")
                .usingGeneratedKeyColumns("id");
    }

    private RowMapper<CartEntity> cartEntityRowMapper() {
        return (resultSet, rowNum) ->
                new CartEntity(
                        resultSet.getLong("id"),
                        resultSet.getLong("member_id"),
                        resultSet.getLong("product_id"),
                        resultSet.getInt("count"),
                        resultSet.getTimestamp("created_at"),
                        resultSet.getTimestamp("updated_at")
                );
    }

    @Override
    public Optional<CartEntity> save(CartEntity cart, Long productId, Long memberId) {
        Map<String, Object> parameterSource = new HashMap<>();

        parameterSource.put("member_id", memberId);
        parameterSource.put("product_id", productId);
        parameterSource.put("count", cart.getCount());
        parameterSource.put("created_at", Timestamp.valueOf(LocalDateTime.now()));

        long id = simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();
        return findById(id);
    }

    @Override
    public Optional<CartEntity> findById(Long id) {
        String sql = "SELECT * FROM cart WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, cartEntityRowMapper(), id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<CartEntity> findByMemberId(Long memberId) {
        String sql = "SELECT * FROM cart WHERE member_id = ?";
        return jdbcTemplate.query(sql, cartEntityRowMapper(), memberId);
    }

    @Override
    public List<CartEntity> findAll() {
        String sql = "SELECT * FROM cart";
        return jdbcTemplate.query(sql, cartEntityRowMapper());
    }

    @Override
    public CartEntity update(CartEntity entity) {
        String sql = "UPDATE cart SET count = ?, updated_at = ? WHERE id = ?";
        jdbcTemplate.update(sql, entity.getCount(), entity.getUpdatedAt(), entity.getId());
        return entity;
    }

    @Override
    public boolean existByIdAndMemberId(Long id, Long memberId) {
        String sql = "SELECT COUNT(*) FROM cart WHERE id = ? AND member_id = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, id, memberId);
        return count > 0;
    }

    @Override
    public boolean existByMemberIdAndProductId(Long memberId, Long productId) {
        String sql = "SELECT COUNT(*) FROM cart WHERE member_id = ? AND product_id = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, memberId, productId);
        return count > 0;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM cart WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
