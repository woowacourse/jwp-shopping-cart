package cart.dao.cart;

import cart.domain.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcCartDao implements CartDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Product> memberRowMapper = (resultSet, rowNum) -> Product.of(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getString("image_url"),
            resultSet.getInt("price")
    );

    @Override
    public void insert(final Long memberId, final Long productId) {
        final String sql = "INSERT INTO cart_product(member_id, product_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, memberId, productId);
    }

    @Override
    public List<Product> findAllProductByMemberId(final Long memberId) {
        final String sql = "SELECT p.id, p.name, p.image_url, p.price FROM cart_product as cp JOIN product as p ON cp.product_id = p.id WHERE member_id = ?";
        return jdbcTemplate.query(sql, memberRowMapper, memberId);
    }

    @Override
    public void deleteByMemberIdAndProductId(final Long memberId, final Long productId) {
        final String sql = "DELETE FROM cart_product WHERE member_id = ? AND product_id = ?";
        jdbcTemplate.update(sql, memberId, productId);
    }
}
