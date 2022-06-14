package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

import woowacourse.shoppingcart.domain.Quantity;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.domain.product.ProductStock;
import woowacourse.shoppingcart.domain.product.vo.ThumbnailImage;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("classpath:schema.sql")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class ProductDaoTest {

    private final ProductDao productDao;

    public ProductDaoTest(JdbcTemplate jdbcTemplate) {
        this.productDao = new ProductDao(jdbcTemplate);
    }

    @DisplayName("Product를 저장하면, id를 반환한다.")
    @Test
    void save() {
        // given
        final String name = "초콜렛";
        final int price = 1_000;
        final ThumbnailImage thumbnailImage = new ThumbnailImage("www.test.com", "이미지");

        // when
        final ProductStock product = productDao.save(new Product(name, price, thumbnailImage), 10);

        // then
        assertThat(product.getId()).isEqualTo(1L);
    }

    @DisplayName("productID를 상품을 찾으면, product를 반환한다.")
    @Test
    void findProductById() {
        // given
        final String name = "초콜렛";
        final int price = 1_000;
        final ThumbnailImage thumbnailImage = new ThumbnailImage("www.test.com", "이미지");
        final ProductStock savedProduct = productDao.save(new Product(name, price, thumbnailImage), 10);
        final Product expectedProduct = new Product(savedProduct.getId(), name, price, thumbnailImage);

        // when
        final Product product = productDao.findProductById(savedProduct.getId());

        // then
        assertThat(product).usingRecursiveComparison().isEqualTo(expectedProduct);
    }

    @DisplayName("상품 목록 조회")
    @Test
    void getProducts() {

        // given
        final int size = 0;

        // when
        final List<Product> products = productDao.findProducts();

        // then
        assertThat(products).size().isEqualTo(size);
    }

    @DisplayName("싱품 삭제")
    @Test
    void deleteProduct() {
        // given
        final String name = "초콜렛";
        final int price = 1_000;
        final ThumbnailImage thumbnailImage = new ThumbnailImage("www.test.com", "이미지");

        final ProductStock savedProduct = productDao.save(new Product(name, price, thumbnailImage), 10);
        final int beforeSize = productDao.findProducts().size();

        // when
        productDao.delete(savedProduct.getId());

        // then
        final int afterSize = productDao.findProducts().size();
        assertThat(beforeSize - 1).isEqualTo(afterSize);
    }

    @DisplayName("주문이 성공될 시 기존 재고에서 주문 수량 뺸 값을 저장한다.")
    @Test
    void updateStock() {
        // given & when
        final String name = "초콜렛";
        final int price = 1_000;
        final ThumbnailImage thumbnailImage = new ThumbnailImage("www.test.com", "이미지");

        // when
        final ProductStock product = productDao.save(new Product(name, price, thumbnailImage), 10);
        productDao.update(new ProductStock(product.getProduct(), new Quantity(2)));

        // then
        ProductStock productStock = productDao.findProductStockById(product.getId());
        assertThat(productStock.getStockQuantity()).isEqualTo(2);
    }
}
