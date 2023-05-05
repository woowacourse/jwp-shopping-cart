package cart.dao.cart;

import cart.entity.CartEntity;
import cart.entity.ItemEntity;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcCartDao implements CartDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCartDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(final String memberEmail, final Long itemId) {
        String sql = "INSERT INTO cart (member_email, item_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, memberEmail, itemId);
        return itemId;
    }

    @Override
    public Optional<CartEntity> findByEmailAndId(final String memberEmail, final Long itemId) {
        String sql = "SELECT item.*,cart.quantity FROM item , cart  WHERE cart.item_id = item.id and member_email = ? and item_id = ?";

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, mapRow(), memberEmail, itemId));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public void update(final String memberEmail, CartEntity cart) {
        String sql = "update cart set quantity = ? where member_email = ? and item_id = ?";
        jdbcTemplate.update(sql, cart.getQuantity(), memberEmail, cart.getItemId());
    }

    @Override
    public Optional<List<CartEntity>> findAll(final String memberEmail) {
        String sql = "SELECT item.*,cart.quantity FROM item , cart  WHERE cart.item_id = item.id and member_email = ?";

        try {
            return Optional.of(jdbcTemplate.query(sql, mapRow(), memberEmail));
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    private RowMapper<CartEntity> mapRow() {
        return (rs, rowNum) -> {
            Long id = rs.getLong("id");
            String name = rs.getString("name");
            String itemUrl = rs.getString("item_url");
            int itemPrice = rs.getInt("price");
            Integer quantity = rs.getInt("quantity");
            return new CartEntity(new ItemEntity(id, name, itemUrl, itemPrice), quantity);
        };
    }

    @Override
    public void delete(final String memberEmail, final Long itemId) {
        String sql = "DELETE FROM cart WHERE member_email = ? and item_id = ?";
        jdbcTemplate.update(sql, memberEmail, itemId);
    }
}
