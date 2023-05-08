package cart.web.controller.product;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;

import cart.product.usecase.DeleteOneProductUseCase;
import cart.web.config.auth.BasicAuthorizedUserArgumentResolver;
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
class DeleteOneProductControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @MockBean
    private DeleteOneProductUseCase deleteProductService;

    @DisplayName("Product id로 상품을 삭제 할 수 있다.")
    @Test
    void deleteProduct() {
        doNothing().when(deleteProductService).deleteSingleProductById(anyLong());

        given().log().all()
                .when().accept(MediaType.APPLICATION_JSON_VALUE)
                .delete("/admin/{deletedId}", 1L)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
