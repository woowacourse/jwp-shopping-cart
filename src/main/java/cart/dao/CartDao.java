package cart.dao;

import cart.dao.dto.CartDto;
import cart.dao.dto.ItemDto;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class CartDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<ItemDto> itemRowMapper = (resultSet, rowNum) -> {
        ItemDto itemDto = new ItemDto(
                resultSet.getLong("item_id"),
                resultSet.getString("name"),
                resultSet.getString("image_url"),
                resultSet.getInt("price")
        );
        return itemDto;
    };

    private final RowMapper<CartDto> cartRowMapper = (resultSet, rowNum) -> {
        CartDto cartDto = new CartDto(
                resultSet.getLong("cart_id"),
                resultSet.getLong("member_id"),
                resultSet.getLong("item_id")
        );
        return cartDto;
    };

    public CartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long insert(Long memberId, Long itemId) {
        String sql = "INSERT INTO CART VALUES (?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"cart_id"});
            ps.setLong(1, memberId);
            ps.setLong(2, itemId);
            return ps;
        }, keyHolder);

        jdbcTemplate.update(sql, memberId, itemId);
        return keyHolder.getKey().longValue();
    }

    public Optional<CartDto> findByMemberIdAndItemId(Long memberId, Long itemId) {
        String sql = "SELECT member_id, item_id FROM CART WHERE member_id = ? and item_id = ?";

        return jdbcTemplate.query(sql, cartRowMapper, memberId, itemId)
                .stream()
                .findAny();
    }

    public List<ItemDto> findAllByMemberId(Long memberId) {
        String sql = "SELECT item_id, name, image_url, price "
                + "FROM CART JOIN ITEM "
                + "ON CART.item_id = ITEM.item_id "
                + "and CART.member_id = ?";

        return jdbcTemplate.query(sql, itemRowMapper, memberId);
    }

    public void delete(Long memberId, Long itemId) {
        String sql = "DELETE FROM CART WHERE member_id = ? and item_id = ?";

        jdbcTemplate.update(sql, memberId, itemId);
    }
}
