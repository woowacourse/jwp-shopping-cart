package cart.web.controller.product;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import cart.product.service.dto.ProductResponseDto;
import cart.product.usecase.FixOneProductInfoUseCase;
import cart.web.config.auth.BasicAuthorizedUserArgumentResolver;
import cart.web.dto.request.ProductModificationRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@MockBean(BasicAuthorizedUserArgumentResolver.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FixOneProductInfoControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @MockBean
    private FixOneProductInfoUseCase fixProductService;


    @DisplayName("Product를 수정할 수 있다.")
    @Test
    void updateProduct() {
        final ProductModificationRequest request =
                new ProductModificationRequest(1L, "Chicken", 18_000, "FOOD", "image.com");
        final ProductResponseDto response =
                new ProductResponseDto(1L, "Chicken", 18_000, "FOOD", "image.com");
        when(fixProductService.fixSingleProductInfo(any())).thenReturn(response);

        given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().put("/admin")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("name", is("Chicken"))
                .body("price", is(18000));
    }

}
