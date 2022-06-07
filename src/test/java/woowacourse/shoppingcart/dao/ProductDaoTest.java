package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Product;

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
        String name = "초콜렛";
        int price = 1_000;
        int stock = 1;
        String imageUrl = "www.test.com";

        Long productId = productDao.save(new Product(name, price, stock, imageUrl));

        assertThat(productId).isEqualTo(1L);
    }

    @DisplayName("productID를 상품을 찾으면, product를 반환한다.")
    @Test
    void findProductById() {
        String name = "초콜렛";
        int price = 1_000;
        int stock = 1;
        String imageUrl = "www.test.com";
        Long productId = productDao.save(new Product(name, price, stock, imageUrl));
        Product expectedProduct = new Product(productId, name, price, stock, imageUrl);

        Product product = productDao.findProductById(productId)
                .orElseGet(() -> fail(""));

        assertThat(product).usingRecursiveComparison().isEqualTo(expectedProduct);
    }

    @DisplayName("상품 목록을 조회한다.")
    @Test
    void findProducts() {
        int size = 0;

        List<Product> products = productDao.findProducts();

        assertThat(products).size().isEqualTo(size);
    }

    @DisplayName("특정 페이지를 페이징해서 상품 목록을 조회한다.")
    @Test
    void findProductsByPaging() {
        int page = 2;
        int limit = 10;

        String name = "초콜렛";
        int price = 1_000;
        String imageUrl = "www.test.com";
        int stock = 1;
        for (int i = 0; i < 10; i++) {
            productDao.save(new Product(name, price, stock, imageUrl));
        }
        String name2 = "아이스크림";
        productDao.save(new Product(name2, price, stock, imageUrl));

        List<Product> products = productDao.findProductsByPaging(page, limit);

        assertThat(products).hasSize(1)
                .extracting(Product::getName)
                .contains(name2);
    }

    @DisplayName("상품 존재 여부를 확인한다.")
    @Test
    void exists() {
        String name = "초콜렛";
        int price = 1_000;
        int stock = 1;
        String imageUrl = "www.test.com";
        Long productId = productDao.save(new Product(name, price, stock, imageUrl));

        boolean exists = productDao.exists(productId);

        assertThat(exists).isTrue();
    }

    @DisplayName("싱품을 삭제한다.")
    @Test
    void deleteProduct() {
        String name = "초콜렛";
        int price = 1_000;
        String imageUrl = "www.test.com";
        int stock = 1;

        Long productId = productDao.save(new Product(name, price, stock, imageUrl));
        int beforeSize = productDao.findProducts().size();

        productDao.delete(productId);

        int afterSize = productDao.findProducts().size();
        assertThat(beforeSize - 1).isEqualTo(afterSize);
    }

    @DisplayName("상품의 재고를 업데이트한다.")
    @Test
    void updateStock() {
        String name = "초콜렛";
        int price = 1_000;
        String imageUrl = "www.test.com";
        int stock = 1;

        Long productId = productDao.save(new Product(name, price, stock, imageUrl));
        Product product = productDao.findProductById(productId)
                .orElseGet(() -> fail(""));;
        product.receive(1);
        productDao.updateStock(product);
        Product updated = productDao.findProductById(productId)
                .orElseGet(() -> fail(""));;

        assertThat(updated.getStock()).isEqualTo(2);
    }

    @DisplayName("저장된 상품 종류의 수를 계산한다.")
    @Test
    void countAll() {
        String name = "초콜렛";
        int price = 1_000;
        String imageUrl = "www.test.com";
        int stock = 1;
        for (int i = 0; i < 10; i++) {
            productDao.save(new Product(name, price, stock, imageUrl));
        }

        int count = productDao.countAll();

        assertThat(count).isEqualTo(10);
    }
}
