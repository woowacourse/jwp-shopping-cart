package cart.service.product;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.dao.ProductDao;
import cart.domain.product.ProductEntity;
import cart.domain.product.ProductId;
import cart.dto.application.ProductDto;
import cart.dto.application.ProductEntityDto;
import io.restassured.RestAssured;
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
class ProductUpdateServiceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ProductUpdateService productUpdateService;

    @Autowired
    private ProductDao productDao;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    @Test
    @CsvSource(value = {"1:applePizza:10000:https://apple.pizza",
            "2:salmonSalad:20000:https://salmon.salad"}, delimiter = ':')
    void 상품_수정() {
        final long id = 1;
        final String newName = "new name";
        final int newPrice = 1234;
        final String newImageUrl = "https://newimage.com";
        final ProductDto newProduct = new ProductDto(newName, newPrice, newImageUrl);
        productUpdateService.updateProduct(new ProductEntityDto(id, newProduct));

        final ProductEntity updatedProduct = productDao.find(new ProductId(id));
        assertAll(
                () -> assertThat(updatedProduct.getName()).isEqualTo(newName),
                () -> assertThat(updatedProduct.getPrice()).isEqualTo(newPrice),
                () -> assertThat(updatedProduct.getImageUrl()).isEqualTo(newImageUrl)
        );
    }

    @Test
    void 존재하지_않는_상품_수정시_예외_발생() {
        assertThatIllegalArgumentException().isThrownBy(
                () -> productUpdateService.updateProduct(
                        new ProductEntityDto(10L, "name", 1234, "https://newimage.com")
                )
        ).withMessage("존재하지 않는 id 입니다.");
    }
}
