package cart.service;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.dao.ProductDao;
import cart.domain.product.Product;
import cart.domain.product.ProductEntity;
import io.restassured.RestAssured;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql("classpath:test.sql")
class ProductServiceTest {

    @LocalServerPort
    private int port;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 모든_상품_목록_조회() {
        final List<ProductEntity> products = productService.findAll();

        assertThat(products.size()).isEqualTo(3);
    }

    @Test
    void 상품_등록() {
        final long expectedId = 4L;
        final String name = "name4";
        final int price = 4000;
        final String imageUrl = "https://image4.com";
        final ProductEntity savedProduct = productService.register(new Product(name, price, imageUrl));

        assertThat(savedProduct.getId()).isEqualTo(expectedId);
    }

    @Test
    @CsvSource(value = {"1:applePizza:10000:https://apple.pizza",
            "2:salmonSalad:20000:https://salmon.salad"}, delimiter = ':')
    void 상품_수정() {
        final long id = 1;
        final String newName = "new name";
        final int newPrice = 1234;
        final String newImageUrl = "https://newimage.com";
        final Product newProduct = new Product(newName, newPrice, newImageUrl);
        productService.updateProduct(id, newProduct);

        final ProductEntity updatedProduct = productDao.find(id);
        assertAll(
                () -> assertThat(updatedProduct.getName()).isEqualTo(newName),
                () -> assertThat(updatedProduct.getPrice()).isEqualTo(newPrice),
                () -> assertThat(updatedProduct.getImageUrl()).isEqualTo(newImageUrl)
        );
    }

    @Test
    void 존재하지_않는_상품_수정시_예외_발생() {
        assertThatIllegalArgumentException().isThrownBy(
                () -> productService.updateProduct(10L, new Product("name", 1234, "https://newimage.com"))
        ).withMessage("존재하지 않는 id 입니다.");
    }

    @Test
    void 상품_삭제() {
        final long id = 1L;
        productService.deleteProduct(id);

        assertThat(productService.findAll().size()).isEqualTo(2);
    }

    @Test
    void 존재하지_않는_상품_삭제시_예외_발생() {
        assertThatIllegalArgumentException().isThrownBy(
                () -> productService.deleteProduct(4L)
        ).withMessage("존재하지 않는 id 입니다.");
    }
}
