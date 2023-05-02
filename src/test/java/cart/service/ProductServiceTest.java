package cart.service;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.dao.ProductDao;
import cart.domain.product.Product;
import cart.dto.application.ProductDto;
import io.restassured.RestAssured;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql("classpath:schema.sql")
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

        productDao.insert(new Product("pizza", 1000,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a3/Eq_it-na_pizza-margherita_sep2005_sml.jpg/800px-Eq_it-na_pizza-margherita_sep2005_sml.jpg"));
        productDao.insert(
                new Product("salad", 2000,
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/9/94/Salad_platter.jpg/1200px-Salad_platter.jpg"));
    }

    @Test
    void 모든_상품_목록_조회() {
        final List<Product> products = productService.findAll();

        assertThat(products.size()).isEqualTo(2);
    }

    @ParameterizedTest
    @CsvSource(value = {"applePizza:10000:https://apple.pizza", "salmonSalad:20000:https://salmon.salad"},
            delimiter = ':')
    void 상품_등록(final String name, final int price, final String imageUrl) {
        final long expectedId = 3L;
        final Product savedProduct = productService.register(new ProductDto(name, price, imageUrl));

        assertThat(savedProduct.getId()).isEqualTo(expectedId);
    }

    @ParameterizedTest
    @CsvSource(value = {"1:applePizza:10000:https://apple.pizza",
            "2:salmonSalad:20000:https://salmon.salad"}, delimiter = ':')
    void 상품_수정(final long id, final String newName, final int newPrice, final String newImageUrl) {
        productService.updateProduct(id, new ProductDto(newName, newPrice, newImageUrl));

        final Product updatedProduct = productDao.findById(id);
        assertAll(
                () -> assertThat(updatedProduct.getName()).isEqualTo(newName),
                () -> assertThat(updatedProduct.getPrice()).isEqualTo(newPrice),
                () -> assertThat(updatedProduct.getImageUrl()).isEqualTo(newImageUrl)
        );
    }

    @Test
    void 존재하지_않는_상품_수정시_예외_발생() {
        assertThatIllegalArgumentException().isThrownBy(
                () -> productService.updateProduct(10L, new ProductDto("name", 1234, "imageUrl"))
        ).withMessage("존재하지 않는 id 입니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void 상품_삭제(final long id) {
        productService.deleteProduct(id);

        assertThat(productService.findAll().size()).isEqualTo(1);
    }

    @Test
    void 존재하지_않는_상품_삭제시_예외_발생() {
        assertThatIllegalArgumentException().isThrownBy(
                () -> productService.deleteProduct(3L)
        ).withMessage("존재하지 않는 id 입니다.");
    }
}
