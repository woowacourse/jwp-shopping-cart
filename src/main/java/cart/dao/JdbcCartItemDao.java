package cart.dao;

import cart.domain.entity.CartItem;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class JdbcCartItemDao implements CartItemDao {

    private final JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert simpleInsert;

    public JdbcCartItemDao(final JdbcTemplate jdbcTemplate, final DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("cart_item")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<CartItem> cartItemEntityRowMapper = (resultSet, rowNum) -> CartItem.of(
            resultSet.getLong("id"),
            resultSet.getLong("member_id"),
            resultSet.getLong("product_id")
    );

    @Override
    public List<CartItem> selectAllByMemberId(final Long memberId) {
        final String sql = "SELECT * FROM cart_item WHERE member_id = ?";
        return jdbcTemplate.query(sql, cartItemEntityRowMapper, memberId);
    }

    @Override
    public long insert(final CartItem cartItem) {
        try {
            SqlParameterSource parameters = new BeanPropertySqlParameterSource(cartItem);
            return simpleInsert.executeAndReturnKey(parameters).longValue();
        } catch (DataAccessException exception) {
            throw new IllegalArgumentException("장바구니에 담을 수 없습니다");
        }
    }

    @Override
    public int deleteByIdAndMemberId(final long id, final long memberId) {
        final String sql = "DELETE FROM cart_item WHERE id = ? AND member_id = ?";
        return jdbcTemplate.update(sql, id, memberId);
    }
}
