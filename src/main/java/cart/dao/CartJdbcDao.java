package cart.dao;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class CartJdbcDao implements CartDao {

    private final JdbcTemplate jdbcTemplate;

    public CartJdbcDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<CartEntity> getCartEntityRowMapper() {
        return (rs, rowNum) -> new CartEntity(
                rs.getInt("id"),
                rs.getInt("product_id"),
                rs.getInt("member_id")
        );
    }

    @Override
    public List<CartEntity> findCartByMemberId(final int memberId) {
        final String sql = "SELECT * FROM CART WHERE member_id = ?";

        return jdbcTemplate.query(sql, getCartEntityRowMapper(), memberId);
    }

    @Override
    public void deleteById(final int cartId) {
        final String sql = "DELETE FROM CART WHERE id = ?";

        jdbcTemplate.update(sql, cartId);
    }

    @Override
    public void addCart(final CartEntity cartEntity) {
        final String sql = "INSERT INTO CART (product_id, member_id) values (?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement pstmt = con.prepareStatement(
                    sql, new String[]{"id"});
            pstmt.setInt(1, cartEntity.getProductId());
            pstmt.setInt(2, cartEntity.getMemberId());
            return pstmt;
        }, keyHolder);
    }

}
