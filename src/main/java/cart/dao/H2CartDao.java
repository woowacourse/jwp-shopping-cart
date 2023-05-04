package cart.dao;

import cart.domain.Cart;
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
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public class H2CartDao implements CartDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final H2ProductDao productDao;
    private final H2MemberDao memberDao;

    public H2CartDao(JdbcTemplate jdbcTemplate, H2ProductDao productDao, H2MemberDao memberDao) {
        this.productDao = productDao;
        this.memberDao = memberDao;
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart")
                .usingGeneratedKeyColumns("id");
    }

    private RowMapper<CartEntity> cartEntityRowMapper() {
        return (resultSet, rowNum) ->
                new CartEntity(
                        resultSet.getLong("id"),
                        memberDao.findById(resultSet.getLong("member_id"))
                                .orElseThrow(() -> new NoSuchElementException("회원 조회 중 문제가 발생했습니다.")),
                        productDao.findById(resultSet.getLong("product_id"))
                                .orElseThrow(() -> new NoSuchElementException("상품 조회 중 문제가 발생했습니다.")),
                        resultSet.getInt("count"),
                        resultSet.getTimestamp("created_at"),
                        resultSet.getTimestamp("updated_at")
                );
    }

    public Optional<CartEntity> save(Cart cart, long productId, long memberId) {
        Map<String, Object> parameterSource = new HashMap<>();

        parameterSource.put("member_id", memberId);
        parameterSource.put("product_id", productId);
        parameterSource.put("count", cart.getCount());
        parameterSource.put("created_at", Timestamp.valueOf(LocalDateTime.now()));

        long id = simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();
        return findById(id);
    }

    public CartEntity update(CartEntity entity) {
        String sql = "UPDATE cart SET count = ?, updated_at = ? WHERE id = ?";
        jdbcTemplate.update(sql, entity.getCount(), Timestamp.valueOf(LocalDateTime.now()), entity.getId());
        return entity;
    }

    public Optional<CartEntity> findById(Long id) {
        String sql = "SELECT * FROM cart WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, cartEntityRowMapper(), id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<CartEntity> findAll() {
        String sql = "SELECT * FROM cart";
        return jdbcTemplate.query(sql, cartEntityRowMapper());
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM cart WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<CartEntity> findByMemberId(long memberId) {
        String sql = "SELECT * FROM cart WHERE member_id = ?";
        return jdbcTemplate.query(sql, cartEntityRowMapper(), memberId);
    }
}
