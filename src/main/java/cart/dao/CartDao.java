package cart.dao;

import cart.entity.CartEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class CartDao {

    private static final RowMapper<CartEntity> cartRowMapper = (resultSet, rowNum) -> new CartEntity(
            resultSet.getLong("member_id"),
            resultSet.getLong("product_id")
    );
    private static final String ALL_COLUMNS = "member_id, product_id";

    private final JdbcTemplate jdbcTemplate;

    public CartDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert(final long memberId, final long productId) {
        String sql = "INSERT INTO CART(member_id, product_id) VALUES (?, ?);";

        jdbcTemplate.update(sql, memberId, productId);
    }

    public List<CartEntity> findByMemberId(final long memberId) {
        String sql = "SELECT " + ALL_COLUMNS + " FROM Cart WHERE member_id = ?;";

        return jdbcTemplate.query(sql, cartRowMapper, memberId);
    }

    public void deleteByProductId(final long productId) {
        String sql = "DELETE FROM Cart WHERE product_id = ?;";

        jdbcTemplate.update(sql, productId);
    }
}
