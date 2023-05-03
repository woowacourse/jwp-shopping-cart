package cart.dao.cart;

import cart.entity.ItemEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcCartDao implements CartDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCartDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(final String memberEmail, final Long itemId) {
        String sql = "INSERT INTO cart VALUES (?, ?)";
        jdbcTemplate.update(sql, memberEmail, itemId);
        return itemId;
    }

    @Override
    public List<ItemEntity> findAll(final String memberEmail) {
        String sql = "SELECT item.* FROM item LEFT JOIN cart ON cart.item_id=item.id WHERE cart.member_email = ?";
        return jdbcTemplate.query(sql, mapRow(), memberEmail);
    }

    private RowMapper<ItemEntity> mapRow() {
        return (rs, rowNum) -> {
            long id = rs.getLong(1);
            String name = rs.getString(2);
            String itemUrl = rs.getString(3);
            int itemPrice = rs.getInt(4);
            return new ItemEntity(id, name, itemUrl, itemPrice);
        };
    }

    @Override
    public void delete(final String memberEmail, final Long itemId) {
        String sql = "DELETE FROM cart WHERE member_email = ? and item_id = ?";
        jdbcTemplate.update(sql, memberEmail, itemId);
    }
}
