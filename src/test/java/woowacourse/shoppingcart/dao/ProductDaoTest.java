package woowacourse.shoppingcart.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Product;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("classpath:schema.sql")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DisplayName("Product DAO 테스트")
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
        final String imageUrl = "www.test.com";

        // when
        final Long productId = productDao.save(new Product(name, price, imageUrl));

        // then
        assertThat(productId).isEqualTo(1L);
    }

    @DisplayName("productID 를 이용하여 상품을 조회한다.")
    @Test
    void findById() {
        // given
        Long productId = productDao.save(new Product("초콜렛", 1_000, "www.test.com"));

        // when
        Product product = productDao.findById(productId).get();

        // then
        assertThat(product).usingRecursiveComparison().isEqualTo(
                new Product(productId, "초콜렛", 1_000, "www.test.com")
        );
    }

    @DisplayName("현재 페이지에 해당되는 상품 목록을 조회한다.")
    @Test
    void findProductsInPage() {
        // given
        productDao.save(new Product("초콜렛", 1_000, "www.test.com"));
        productDao.save(new Product("초콜렛2", 1_000, "www.test2.com"));
        productDao.save(new Product("초콜렛3", 1_000, "www.test3.com"));
        productDao.save(new Product("초콜렛4", 1_000, "www.test4.com"));

        // when
        List<Product> products = productDao.findProductsInPage(1L, 5L);
        List<String> productNames = products.stream()
                .map(Product::getName)
                .collect(Collectors.toList());

        // then
        assertAll(
                () -> assertThat(products).size().isEqualTo(4),
                () -> assertThat(productNames).contains("초콜렛", "초콜렛2", "초콜렛3", "초콜렛4")
        );
    }

    @DisplayName("싱품 삭제")
    @Test
    void deleteProduct() {
        // given
        final String name = "초콜렛";
        final int price = 1_000;
        final String imageUrl = "www.test.com";

        final Long productId = productDao.save(new Product(name, price, imageUrl));
        final int beforeSize = productDao.findProducts().size();

        // when
        productDao.delete(productId);

        // then
        final int afterSize = productDao.findProducts().size();
        assertThat(beforeSize - 1).isEqualTo(afterSize);
    }
}
