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

    @DisplayName("상품을 저장한다.")
    @Test
    void save() {
        // when
        Long productId = productDao.save(new Product("초콜렛", 1_000, "www.test.com"));

        // then
        Product product = productDao.findById(productId).get();

        assertAll(
                () -> assertThat(product.getId()).isEqualTo(productId),
                () -> assertThat(product.getName()).isEqualTo("초콜렛"),
                () -> assertThat(product.getPrice()).isEqualTo(1_000),
                () -> assertThat(product.getImageUrl()).isEqualTo("www.test.com")
        );
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

    @DisplayName("싱품을 삭제한다.")
    @Test
    void delete() {
        // given
        Long productId = productDao.save(new Product("초콜렛", 1_000, "www.test.com"));

        // when
        productDao.delete(productId);

        // then
        assertThat(productDao.findById(productId).isEmpty()).isTrue();
    }
}
