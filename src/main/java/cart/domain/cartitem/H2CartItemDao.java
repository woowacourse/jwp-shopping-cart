package cart.domain.cartitem;

import cart.dto.CartItemDto;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class H2CartItemDao extends CartItemDao {

    private static final RowMapper<CartItemDto> CART_ITEM_DTO_ROW_MAPPER = (resultSet, rowNum) -> new CartItemDto(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getString("image_url"),
            resultSet.getInt("price")
    );

    private static final RowMapper<CartItem> CART_ITEM_ROW_MAPPER = (resultSet, rowNum) -> new CartItem(
            resultSet.getLong("id"),
            resultSet.getLong("product_id"),
            resultSet.getLong("member_id")
    );

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public H2CartItemDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart_items")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public CartItem insert(final CartItem entity) {
        Number id = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(
                Map.of("product_id", entity.getProductId(),
                        "member_id", entity.getMemberId())
        ));
        return new CartItem(id.longValue(), entity.getMemberId(), entity.getProductId());
    }

    @Override
    public boolean isExist(final Long id) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
                "SELECT EXISTS (SELECT id FROM cart_items WHERE id = ? limit 1) AS SUCCESS", Boolean.class, id));
    }

    @Override
    public Optional<CartItem> findById(final Long id) {
        CartItem cartItem = jdbcTemplate.queryForObject(
                "SELECT id, product_id, member_id "
                        + "FROM cart_items "
                        + "WHERE id = ?", CART_ITEM_ROW_MAPPER, id);
        return Optional.ofNullable(cartItem);
    }

    @Override
    public void deleteById(final Long id) {
        jdbcTemplate.update("DELETE FROM cart_items WHERE id = ?", id);
    }

    @Override
    List<CartItemDto> findByMemberId(final Long id) {
        return jdbcTemplate.query(
                "SELECT c.id, p.name, p.image_url, p.price "
                        + "FROM cart_items c, products p "
                        + "WHERE c.product_id = p.id "
                        + "AND c.member_id = ?", CART_ITEM_DTO_ROW_MAPPER, id);
    }

    @Override
    boolean isDuplicated(final Long member_id, final Long product_id) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
                "SELECT EXISTS (SELECT id FROM cart_items "
                        + "WHERE member_id = ? "
                        + "AND product_id = ? limit 1) AS SUCCESS",
                Boolean.class,
                member_id, product_id));
    }
}
