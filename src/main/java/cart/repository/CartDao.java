package cart.repository;

import static cart.repository.mapper.EntityRowMapper.productRowMapper;

import cart.entity.ProductEntity;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CartDao {
    private final JdbcTemplate jdbcTemplate;

    public CartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Long memberId, Long productId) {
        String sql = "INSERT INTO CART(member_id, product_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, memberId, productId);
    }

    public List<ProductEntity> findAllProductByMemberId(Long memberId) {
        String sql = "SELECT P.id, P.name, P.price, P.image_url " +
                "FROM CART as C " +
                "JOIN MEMBER as M ON C.member_id = M.id " +
                "JOIN PRODUCT P ON C.product_id = P.id " +
                "WHERE C.member_id = ?";
        return jdbcTemplate.query(sql, productRowMapper(), memberId);
    }

    public void delete(Long memberId, Long productId) {
        String sql = "DELETE FROM CART WHERE member_id = ? AND product_id = ?";
        jdbcTemplate.update(sql, memberId, productId);
    }
}
