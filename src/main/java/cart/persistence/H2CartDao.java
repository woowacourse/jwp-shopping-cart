package cart.persistence;

import cart.domain.Item;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Repository
public class H2CartDao implements CartDao {

    private final JdbcTemplate jdbcTemplate;

    public H2CartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long createItem(Long memberId, Long productId) {
        String sql = "INSERT INTO CART(member_id, product_id) VALUES(?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, memberId);
            ps.setLong(2, productId);
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public Item findItemById(Long id) {
        String sql = "SELECT id, member_id, product_id FROM CART WHERE id=?";
        return jdbcTemplate.queryForObject(sql, (resultSet, rowNum) -> new Item(
                resultSet.getLong("id"),
                resultSet.getLong("member_id"),
                resultSet.getLong("product_id")
        ), id);
    }

    @Override
    public List<Long> findAllItems(Long memberId) {
        String sql = "SELECT product_id FROM CART WHERE member_id=?";
        return jdbcTemplate.queryForList(sql, Long.class, memberId);
    }
}
