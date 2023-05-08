package cart.web.controller.product;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import cart.product.usecase.EnrollOneProductUseCase;
import cart.web.config.auth.BasicAuthorizedUserArgumentResolver;
import cart.web.dto.request.ProductCreationRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@MockBean(BasicAuthorizedUserArgumentResolver.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EnrollOneProductControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EnrollOneProductUseCase enrollProductService;


    @DisplayName("POST 요청시, 상품을 등록할 수 있다.")
    @Test
    void postProduct() throws JsonProcessingException {
        final String productName = "ProductA";
        final int productPrice = 18_000;
        final ProductCreationRequest request =
                new ProductCreationRequest(productName, productPrice, "FOOD", "image.com");
        when(enrollProductService.enroll(any()))
                .thenReturn(1L);

        given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(objectMapper.writeValueAsBytes(request))
                .when().post("/admin")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", is(1))
                .body("name", is(productName))
                .body("price", is(productPrice));
    }
}
