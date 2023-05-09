package cart.service.product;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import cart.dto.application.ProductDto;
import cart.dto.application.ProductEntityDto;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql("classpath:test.sql")
class ProductEnrollServiceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ProductEnrollService productEnrollService;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    @Test
    void 상품_등록() {
        final long expectedId = 4L;
        final String name = "name4";
        final int price = 4000;
        final String imageUrl = "https://image4.com";
        final ProductEntityDto savedProduct = productEnrollService.register(new ProductDto(name, price, imageUrl));

        assertThat(savedProduct.getId()).isEqualTo(expectedId);
    }
}
