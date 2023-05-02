package cart.dao;

import cart.dao.entity.CartEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Repository
public class CartDao {

    private static final RowMapper<CartEntity> CART_ENTITY_ROW_MAPPER = (resultSet, rowNum) -> new CartEntity(
            resultSet.getLong("id"),
            resultSet.getLong("member_id"),
            resultSet.getLong("product_id")
    );
    private static final String[] GENERATED_ID_COLUMN = {"id"};

    private final JdbcTemplate jdbcTemplate;

    public CartDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long insert(final CartEntity cartEntity) {
        final String sql = "INSERT INTO cart (member_id, product_id) VALUES (?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            final PreparedStatement preparedStatement = con.prepareStatement(sql, GENERATED_ID_COLUMN);
            preparedStatement.setLong(1, cartEntity.getMemberId());
            preparedStatement.setLong(2, cartEntity.getProductId());
            return preparedStatement;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public CartEntity findById(final Long id) {
        final String sql = "SELECT * FROM cart WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, CART_ENTITY_ROW_MAPPER, id);
    }

    public List<CartEntity> findAllByMemberId(final Long memberId) {
        final String sql = "SELECT * FROM cart WHERE member_id = ?";
        return jdbcTemplate.query(sql, CART_ENTITY_ROW_MAPPER, memberId);
    }

    public int delete(final Long memberId, final Long productId) {
        final String sql = "DELETE FROM cart WHERE member_id = ? AND product_id = ?";
        return jdbcTemplate.update(sql, memberId, productId);
    }
}
