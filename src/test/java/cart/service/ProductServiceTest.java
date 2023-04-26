package cart.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.dao.ProductDao;
import cart.domain.Product;
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
    @CsvSource(value = {"applePizza:10000:사과피자 이미지", "salmonSalad:20000:연어 샐러드 이미지"}, delimiter = ':')
    void 상품_등록(final String name, final int price, final String imageUrl) {
        final long expectedId = 3L;
        final Long savedId = productService.register(name, price, imageUrl);

        assertThat(savedId).isEqualTo(expectedId);
    }

    @ParameterizedTest
    @CsvSource(value = {"1:applePizza:10000:사과피자 이미지", "2:salmonSalad:20000:연어 샐러드 이미지"}, delimiter = ':')
    void 상품_수정(final long id, final String newName, final int newPrice, final String newImageUrl) {
        productService.updateProduct(id, newName, newPrice, newImageUrl);

        final Product updatedProduct = productDao.findById(id);
        assertAll(
                () -> assertThat(updatedProduct.getName()).isEqualTo(newName),
                () -> assertThat(updatedProduct.getPrice()).isEqualTo(newPrice),
                () -> assertThat(updatedProduct.getImageUrl()).isEqualTo(newImageUrl)
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void 상품_삭제(final long id) {
        productService.deleteProduct(id);

        assertThat(productService.findAll().size()).isEqualTo(1);
    }
}
