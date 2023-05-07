package cart.web.controller.cart;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;

import cart.cart.usecase.SaveOneProductInCartUseCase;
import cart.web.config.auth.BasicAuthorizedUserArgumentResolver;
import cart.web.dto.request.ProductInCartAdditionRequest;
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
class SaveProductInCartControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SaveOneProductInCartUseCase saveOneProductInCartUseCase;


    @DisplayName("사용자 장바구니에 상품을 등록 할 수 있다.")
    @Test
    void addProductInCart() throws JsonProcessingException {
        doNothing().when(saveOneProductInCartUseCase).addSingleProductInCart(any(), anyLong());
        final ProductInCartAdditionRequest request = new ProductInCartAdditionRequest(1L);

        given().log().all()
                .auth().preemptive().basic("a@a.com", "password1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(objectMapper.writeValueAsBytes(request))
                .when().post("/cart")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }
}
