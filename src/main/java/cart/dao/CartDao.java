package cart.dao;

import cart.entity.CartEntity;
import cart.entity.ProductEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartDao {

    private final JdbcTemplate jdbcTemplate;

    public CartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(final Long memberId, final Long productId) {
        String sql = "INSERT INTO CART (member_id, product_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, memberId, productId);
    }

    public List<CartEntity> findAll() {
        String sql = "SELECT * FROM CART";
        return jdbcTemplate.query(sql, (rs, rowNum)
                        -> new CartEntity(
                        rs.getLong("id"),
                        rs.getLong("member_id"),
                        rs.getLong("product_id")
                )
        );
    }

    public List<ProductEntity> findAllByMemberId(Long memberId) {
        String sql = "SELECT * FROM product " +
                "INNER JOIN cart " +
                "ON cart.product_id = product.id " + // 수정된 부분: cart.id 대신 cart.product_id 사용
                "WHERE member_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum)
                -> new ProductEntity(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("image_url"),
                rs.getInt("price")
        ), memberId);
    }

}
