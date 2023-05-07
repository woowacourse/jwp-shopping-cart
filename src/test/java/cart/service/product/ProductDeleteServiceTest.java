package cart.service.product;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import cart.dao.ProductDao;
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
class ProductDeleteServiceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ProductDeleteService productDeleteService;

    @Autowired
    private ProductDao productDao;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    @Test
    void 상품_삭제() {
        final long id = 1L;
        productDeleteService.deleteProduct(id);

        assertThat(productDao.findAll().size()).isEqualTo(2);
    }

    @Test
    void 존재하지_않는_상품_삭제시_예외_발생() {
        assertThatIllegalArgumentException().isThrownBy(
                () -> productDeleteService.deleteProduct(4L)
        ).withMessage("존재하지 않는 id 입니다.");
    }
}
