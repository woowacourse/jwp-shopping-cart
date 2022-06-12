package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.exception.InvalidProductException;

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
        Product product = Product.builder()
                .productName("초콜렛")
                .price(1_000)
                .stock(100)
                .imageUrl("www.test.com")
                .build();

        final Long productId = productDao.save(product);

        assertThat(productId).isEqualTo(1L);
    }

    @DisplayName("productID를 상품을 찾으면, product를 반환한다.")
    @Test
    void findProductById() {
        Product product = Product.builder()
                .productName("초콜렛")
                .price(1_000)
                .stock(100)
                .imageUrl("www.test.com")
                .build();
        final Long productId = productDao.save(product);
        final Product expectedProduct = Product.builder()
                .id(productId)
                .productName("초콜렛")
                .price(1_000)
                .stock(100)
                .imageUrl("www.test.com")
                .build();

        final Product result = productDao.findProductById(productId);

        assertThat(result).usingRecursiveComparison().isEqualTo(expectedProduct);
    }

    @DisplayName("상품 목록 조회")
    @Test
    void getProducts() {
        final int size = 0;

        final List<Product> products = productDao.findProducts();

        assertThat(products).size().isEqualTo(size);
    }

    @DisplayName("상품 재고 업데이트")
    @Test
    void updateStock() {
        Product product = Product.builder()
                .productName("초콜렛")
                .price(1_000)
                .stock(100)
                .imageUrl("www.test.com")
                .build();
        Long productId = productDao.save(product);
        Product savedProduct = Product.builder()
                .id(productId)
                .productName("초콜렛")
                .price(1_000)
                .stock(100)
                .imageUrl("www.test.com")
                .build();

        savedProduct.reduceStock(10);
        productDao.updateStock(savedProduct);

        Product result = productDao.findProductById(productId);
        assertThat(result.getStock()).isEqualTo(90);
    }

    @DisplayName("싱품 삭제")
    @Test
    void deleteProduct() {
        Product product = Product.builder()
                .productName("초콜렛")
                .price(1_000)
                .stock(100)
                .imageUrl("www.test.com")
                .build();

        final Long productId = productDao.save(product);
        final int beforeSize = productDao.findProducts().size();

        productDao.delete(productId);

        final int afterSize = productDao.findProducts().size();
        assertThat(beforeSize - 1).isEqualTo(afterSize);
    }

    @DisplayName("존재하지 않는 상품을 삭제할 시 예외가 발생")
    @Test
    void deleteProduct_productNotExist_throwException() {
        Product product = Product.builder()
                .productName("초콜렛")
                .price(1_000)
                .stock(100)
                .imageUrl("www.test.com")
                .build();
        final Long productId = productDao.save(product);
        productDao.delete(productId);

        assertThatThrownBy(() -> productDao.delete(productId))
                .isInstanceOf(InvalidProductException.class)
                .hasMessage("이미 존재하지 않는 상품입니다");
    }
}
