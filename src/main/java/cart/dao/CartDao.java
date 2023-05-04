package cart.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import cart.domain.Product;

@Repository
public class CartDao {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Product> cartItemRowMapper = (resultSet, rowNum) -> new Product(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getString("image_url"),
            resultSet.getInt("price")
    );

    public CartDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Product> findCartItemsByMemberEmail(final String email) {
        String sql = "SELECT cart.id, product.name, product.image_url, product.price " +
                "FROM cart, product, member " +
                "WHERE product.id = cart.product_id " +
                "AND cart.member_id = member.id " +
                "AND member.email = ?";

        return jdbcTemplate.query(sql, cartItemRowMapper, email);
    }

    public void saveCartItemByMemberEmail(final String email, final Long productId) {
        String selectMemberIdSql = "SELECT id FROM member where email = ?";
        Long memberId = jdbcTemplate.queryForObject(selectMemberIdSql, Long.class, email);

        String insertCartsql = "INSERT INTO cart (member_id, product_id) VALUES (?, ?)";
        jdbcTemplate.update(insertCartsql, memberId, productId);
    }
}
