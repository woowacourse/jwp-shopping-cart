package cart.web.controller;

import cart.domain.service.AdminService;
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

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

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
        ProductModificationRequest request =
                new ProductModificationRequest(productName, productPrice, "FOOD", "image.com");
        when(adminService.save(any()))
                .thenReturn(1L);

        given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ObjectMapper().writeValueAsBytes(request))
                .when().post("/admin/products")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", is("/products/" + 1))
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
                .delete("/admin/products/{id}", 1L)
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("deletedId", is(1));
    }

    @DisplayName("Product를 수정할 수 있다.")
    @Test
    void updateProduct() {
        ProductModificationRequest request =
                new ProductModificationRequest("Chicken", 18_000, "FOOD", "image.com");
        when(adminService.update(any())).thenReturn(1);

        given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().patch("/admin/products/{id}", 1L)
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("name", is("Chicken"))
                .body("price", is(18000));
    }
}
