package cart.dao;

import cart.entity.CartEntity;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@AllArgsConstructor
public class CartDao {

    private static final String MEMBER_ID = "member_id";
    private static final String PRODUCT_ID = "product_id";
    private static final String ALL_COLUMNS = String.join(", ", MEMBER_ID, PRODUCT_ID);

    private static final RowMapper<CartEntity> cartRowMapper = (resultSet, rowNum) -> new CartEntity(
            resultSet.getLong(MEMBER_ID),
            resultSet.getLong(PRODUCT_ID)
    );

    private final JdbcTemplate jdbcTemplate;

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

    public void delete(final long memberId, final long productId) {
        String sql = "DELETE FROM Cart WHERE member_id = ? and product_id = ?;";

        jdbcTemplate.update(sql, memberId, productId);
    }
}
