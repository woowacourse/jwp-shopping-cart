package cart.web.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import cart.domain.product.service.AdminService;
import cart.domain.product.service.dto.ProductDto;
import cart.web.controller.dto.request.ProductCreationRequest;
import cart.web.controller.dto.request.ProductModificationRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AdminRestControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @MockBean
    private AdminService adminService;

    @DisplayName("POST 요청시, 상품을 등록할 수 있다.")
    @Test
    void postProduct() throws JsonProcessingException {
        String productName = "ProductA";
        int productPrice = 18_000;
        ProductCreationRequest request =
                new ProductCreationRequest(productName, productPrice, "FOOD", "image.com");
        when(adminService.save(any()))
                .thenReturn(1L);

        given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ObjectMapper().writeValueAsBytes(request))
                .when().post("/admin")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", is(1))
                .body("name", is(productName))
                .body("price", is(productPrice));
    }

    @DisplayName("Product id로 상품을 삭제 할 수 있다.")
    @Test
    void deleteProduct() {
        doNothing().when(adminService).delete(anyLong());

        given().log().all()
                .when().accept(MediaType.APPLICATION_JSON_VALUE)
                .delete("/admin/{deletedId}", 1L)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("Product를 수정할 수 있다.")
    @Test
    void updateProduct() {
        ProductModificationRequest request =
                new ProductModificationRequest(1L, "Chicken", 18_000, "FOOD", "image.com");
        ProductDto response =
                new ProductDto(1L, "Chicken", 18_000, "FOOD", "image.com");
        when(adminService.update(any())).thenReturn(response);

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
