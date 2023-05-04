package cart.dao;

import cart.dao.entity.CartEntity;
import cart.dao.entity.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static cart.dao.ObjectMapper.getProductRowMapper;

@Repository
public class CartDao {

    private static final boolean DOES_NOT_HAVE_SAME_PRODUCTS = false;
    private static final boolean HAS_SAME_PRODUCTS = true;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ProductEntity> findProductsByMemberId(final Long memberId) {
        final String query = "SELECT product_id as id, name, price, image " +
                "FROM cart " +
                "JOIN product ON cart.product_id = product.id " +
                "JOIN member ON cart.member_id = member.id " +
                "WHERE member_id = ?";
        return jdbcTemplate.query(query, getProductRowMapper(), memberId);
    }

    public void add(final CartEntity cartEntity) {
        final String query = "INSERT INTO CART (member_id, product_id) VALUES (?, ?)";
        jdbcTemplate.update(query, cartEntity.getMemberId(), cartEntity.getProductId());
    }

    public void deleteProduct(final CartEntity cartEntity) {
        final String query = "DELETE FROM CART WHERE member_id = ? AND product_id = ?";
        jdbcTemplate.update(query, cartEntity.getMemberId(), cartEntity.getProductId());
    }

    public boolean hasSameProduct(final Long memberId, final Long productId) {
        try {
            final String query = "SELECT * FROM CART WHERE member_id = ? AND product_id = ?";
            jdbcTemplate.queryForObject(query, Long.class, memberId, productId);
            return HAS_SAME_PRODUCTS;
        } catch (EmptyResultDataAccessException exception) {
            return DOES_NOT_HAVE_SAME_PRODUCTS;
        }
    }
}
