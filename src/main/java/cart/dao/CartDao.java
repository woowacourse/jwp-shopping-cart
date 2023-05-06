package cart.dao;

<<<<<<< HEAD
<<<<<<< HEAD
import cart.entity.CartEntity;
import cart.entity.ProductEntity;
<<<<<<< HEAD
=======
import cart.entity.CartEntity;
>>>>>>> db0c1803 (feat: CartDao save 테스트)
=======
>>>>>>> 28a6d971 (feat: findAllByMemberId 구현)
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

<<<<<<< HEAD
=======
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

>>>>>>> ed825fb4 (feat: 장바구니에 상품 추가)
=======
>>>>>>> db0c1803 (feat: CartDao save 테스트)
@Component
public class CartDao {

    private final JdbcTemplate jdbcTemplate;

    public CartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

<<<<<<< HEAD
<<<<<<< HEAD
    public void save(final Long memberId, final Long productId) {
=======
    public void create(final Long memberId, final Long productId) {
>>>>>>> ed825fb4 (feat: 장바구니에 상품 추가)
=======
    public void save(final Long memberId, final Long productId) {
>>>>>>> db0c1803 (feat: CartDao save 테스트)
        String sql = "INSERT INTO CART (member_id, product_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, memberId, productId);
    }

<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> db0c1803 (feat: CartDao save 테스트)
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

<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 28a6d971 (feat: findAllByMemberId 구현)
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

<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 46ded3a7 (feat: 장바구니 상품 삭제)
    public void deleteById(final Long id) {
        String sql = "DELETE FROM CART WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

<<<<<<< HEAD
=======
>>>>>>> ed825fb4 (feat: 장바구니에 상품 추가)
=======
>>>>>>> db0c1803 (feat: CartDao save 테스트)
=======
>>>>>>> 28a6d971 (feat: findAllByMemberId 구현)
=======
>>>>>>> 46ded3a7 (feat: 장바구니 상품 삭제)
}
