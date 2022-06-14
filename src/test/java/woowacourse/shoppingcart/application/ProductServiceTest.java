package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.cart.Product;
import woowacourse.shoppingcart.dto.product.ProductRequest;
import woowacourse.shoppingcart.exception.ProductNotFoundException;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class ProductServiceTest {

    private final ProductDao productDao;
    private final ProductService productService;

    public ProductServiceTest(JdbcTemplate jdbcTemplate) {
        productDao = new ProductDao(jdbcTemplate);
        productService = new ProductService(productDao);
    }

    @DisplayName("Product 를 추가한다.")
    @Test
    void save() {
        // given
        ProductRequest request = new ProductRequest("url", "roma", 10000, 5);

        // when
        Long savedId = productService.addProduct(request);
        Product product = productDao.findProductById(savedId);

        // then
        assertThat(product).usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(request.toEntity());
    }

    @DisplayName("Product 를 조회한다.")
    @Test
    void find() {
        // when
        Product product = productDao.findProductById(1L);
        Product expected = new Product("[든든] 칠레산 코호 냉동 연어필렛 trim D(껍질있음) 1.1~1.3kg", 24500,
            "https://cdn-mart.baemin.com/sellergoods/main/92438f0e-0c4b-425e-b03b-999cee7cdca2.jpg", 10);
        // then
        assertThat(product).usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(expected);
    }

    @DisplayName("존재하지 않는 ID로 Product 를 조회하면 예외를 던진다.")
    @Test
    void find_notFound() {
        // when, then
        assertThatThrownBy(() -> productDao.findProductById(99L))
            .isInstanceOf(ProductNotFoundException.class);
    }

    @DisplayName("전체 Product 목록을 조회한다.")
    @Test
    void findProducts() {
        // when
        List<Product> products = productService.findProducts();

        // then
        assertThat(products).hasSize(30);
    }

    @DisplayName("Product 를 삭제한다.")
    @Test
    void delete() {
        // when
        productService.deleteProductById(1L);

        // then
        assertThatThrownBy(() -> productDao.findProductById(1L))
            .isInstanceOf(ProductNotFoundException.class);
    }

    @DisplayName("존재하지 않는 ID로 Product 를 삭제하면 예외를 던진다.")
    @Test
    void delete_notFound() {
        // when, then
        assertThatThrownBy(() -> productService.deleteProductById(99L))
            .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    @DisplayName("Product 의 재고를 업데이트 한다.")
    void updateQuantity() {
        //when
        productService.updateProductQuantity(1L, 7);

        //then
        int actual = productService.findProductById(1L).getQuantity();
        assertThat(actual).isEqualTo(7);
    }

    @DisplayName("존재하지 않는 ID로 Product 재고를 업데이트하면 예외를 던진다.")
    @Test
    void updateQuantity_notFound() {
        // when, then
        assertThatThrownBy(() -> productService.updateProductQuantity(99L, 7))
            .isInstanceOf(ProductNotFoundException.class);
    }
}
