package cart.dao;

import cart.domain.Cart;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CartDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CartDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<List<Cart>> findAllByMemberId(final Long memberId) {
        final String sql = "SELECT * FROM CART WHERE member_id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.query(sql, cartRowMapper(), memberId));
        } catch (EmptyResultDataAccessException error) {
            return Optional.empty();
        }
    }

    private RowMapper<Cart> cartRowMapper() {
        return (resultSet, rowNum) -> new Cart(
                resultSet.getLong("id"),
                resultSet.getLong("product_id"),
                resultSet.getLong("member_id")
        );
    }

    public int insert(final Long productId, final Long memberId) {
        final String sql = "INSERT INTO CART (product_id, member_id) VALUES (?,?)";
        return jdbcTemplate.update(sql, productId, memberId);
    }

    public int delete(final Long productId, final Long memberId) {
        final String sql = "DELETE FROM CART WHERE product_id = ? and member_id = ?";
        return jdbcTemplate.update(sql, productId, memberId);
    }

    public Optional<Cart> find(final Long productId, Long memberId) {
        final String sql = "SELECT * FROM CART WHERE product_id = ? and member_id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, cartRowMapper(), productId, memberId));
        } catch (EmptyResultDataAccessException error) {
            return Optional.empty();
        }
    }
}
