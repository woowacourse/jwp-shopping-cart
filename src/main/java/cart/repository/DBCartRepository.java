package cart.repository;

import cart.entity.CartEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class DBCartRepository implements CartRepository {

    private final JdbcTemplate jdbcTemplate;

    public DBCartRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public CartEntity save(CartEntity cartEntity) {
        String sql = "INSERT INTO cart (member_id, product_id) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            Long memberId = cartEntity.getMemberId();
            Long productId = cartEntity.getProductId();
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setLong(1, memberId);
            preparedStatement.setLong(2, productId);
            return preparedStatement;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        return new CartEntity(id, cartEntity.getMemberId(), cartEntity.getProductId());
    }

    @Override
    public List<CartEntity> findByUserId(Long userId) {
        String sql = "SELECT cart.id, member.id, product.id " +
                "FROM cart " +
                "JOIN member ON cart.member_id = member.id " +
                "JOIN product ON cart.product_id = product.id " +
                "WHERE member.id = ?";
        return jdbcTemplate.query(sql, cartEntityMaker(), userId);
    }

    private static RowMapper<CartEntity> cartEntityMaker() {
        return (rs, rowNum) -> {
            Long id = rs.getLong("cart.id");
            Long memberId = rs.getLong("member.id");
            Long productId = rs.getLong("product.id");

            return new CartEntity(id, memberId, productId);
        };
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM cart WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
