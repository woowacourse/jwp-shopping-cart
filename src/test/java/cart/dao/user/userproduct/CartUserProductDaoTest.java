package cart.dao.user.userproduct;

import static org.assertj.core.api.Assertions.assertThat;

import cart.dao.product.ProductDao;
import cart.dao.product.ProductEntity;
import cart.dao.user.CartUserDao;
import cart.dao.user.CartUserEntity;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

@Import({CartUserProductDao.class, CartUserDao.class, ProductDao.class})
@AutoConfigureTestDatabase(replace = Replace.NONE)
@JdbcTest
class CartUserProductDaoTest {
    @Autowired
    private CartUserProductDao cartUserProductDao;

    @Autowired
    private CartUserDao cartUserDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @DisplayName("사용자가 상품을 추가할 수 있다.")
    @Test
    void insertCartUserProduct() {
        Long userId = cartUserDao.insert(new CartUserEntity("a@a.com", "password"));
        Long productId = productDao.insert(new ProductEntity("ProductA", 13_000, "ETC", "image.com"));
        CartUserProductEntity cartUserProductEntity = new CartUserProductEntity(
                userId,
                productId
        );

        cartUserProductDao.insert(cartUserProductEntity);

        List<CartUserProductEntity> entities = jdbcTemplate.query("SELECT * FROM cart_user_product", (rs, rowNum) ->
                new CartUserProductEntity(rs.getLong("cart_user_id"), rs.getLong("product_id")));
        assertThat(entities).hasSize(1);
    }
}
