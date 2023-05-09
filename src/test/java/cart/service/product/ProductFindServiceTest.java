package cart.service.product;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import cart.dto.application.ProductEntityDto;
import io.restassured.RestAssured;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql("classpath:test.sql")
class ProductFindServiceTest {

    @LocalServerPort
    private int port;
    @Autowired
    private ProductFindService productFindService;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 모든_상품_목록_조회() {
        final List<ProductEntityDto> products = productFindService.findAll();

        assertThat(products.size()).isEqualTo(3);
    }
}
