package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.entity.CartItemEntity;
import woowacourse.shoppingcart.entity.CustomerEntity;
import woowacourse.shoppingcart.entity.ProductEntity;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("classpath:schema.sql")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class ProductDaoTest {

    private final ProductDao productDao;
    private final CustomerDao customerDao;
    private final CartItemDao cartItemDao;

    public ProductDaoTest(JdbcTemplate jdbcTemplate,
            NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.productDao = new ProductDao(jdbcTemplate);
        this.customerDao = new CustomerDao(namedParameterJdbcTemplate);
        this.cartItemDao = new CartItemDao(jdbcTemplate);
    }

    @DisplayName("Product를 저장하면, id를 반환한다.")
    @Test
    void save() {
        // given
        final String name = "초콜렛";
        final int price = 1_000;
        final String imageUrl = "www.test.com";

        // when
        final Long productId = productDao.save(new ProductEntity(name, price, imageUrl));

        // then
        assertThat(productId).isEqualTo(1L);
    }

    @DisplayName("productID를 상품을 찾으면, product를 반환한다.")
    @Test
    void findProductById() {
        // given
        final String name = "초콜렛";
        final int price = 1_000;
        final String imageUrl = "www.test.com";
        final Long productId = productDao.save(new ProductEntity(name, price, imageUrl));
        final ProductEntity expectedProduct = new ProductEntity(productId, name, price, imageUrl);

        // when
        final ProductEntity product = productDao.findProductById(productId).get();

        // then
        assertThat(product).usingRecursiveComparison().isEqualTo(expectedProduct);
    }

    @DisplayName("상품 목록 조회")
    @Test
    void getProducts() {

        // given
        final int size = 0;

        // when
        final List<ProductEntity> products = productDao.findProducts();

        // then
        assertThat(products).size().isEqualTo(size);
    }

    @DisplayName("Customer Id를 넣으면, 고객이 주문한 장바구니 목록을 가져온다.")
    @Test
    void findIdsByCustomerId() {
        // given
        final String name = "초콜렛";
        final int price = 1_000;
        final String imageUrl = "www.test.com";

        Long productId = productDao.save(new ProductEntity(name, price, imageUrl));
        Long customerId = customerDao
                .save(new CustomerEntity("yeonlog", "연로그", "asdf1234!", "연로그네", "01050505050"));

        cartItemDao.addCartItem(new CartItemEntity(customerId, productId));

        // when
        List<ProductEntity> productEntities = productDao.findByCartByCustomerId(customerId);

        // then
        assertThat(productEntities).hasSize(1);
    }

    @DisplayName("싱품 삭제")
    @Test
    void deleteProduct() {
        // given
        final String name = "초콜렛";
        final int price = 1_000;
        final String imageUrl = "www.test.com";

        final Long productId = productDao.save(new ProductEntity(name, price, imageUrl));
        final int beforeSize = productDao.findProducts().size();

        // when
        productDao.delete(productId);

        // then
        final int afterSize = productDao.findProducts().size();
        assertThat(beforeSize - 1).isEqualTo(afterSize);
    }
}
