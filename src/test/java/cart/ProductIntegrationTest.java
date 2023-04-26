package cart;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import cart.product.dto.RequestProductDto;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductIntegrationTest {
    
    @LocalServerPort
    private int port;
    
    @BeforeEach
    void setUp() {
        RestAssured.port = this.port;
    }
    
    @Test
    public void getProducts() {
        final var result = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/")
                .then()
                .extract();
        
        assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
    
    @Test
    public void create() {
        final RequestProductDto requestProductDto = new RequestProductDto("망고", "http://mango", 1000);
        final var result = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestProductDto)
                .when()
                .post("/admin/product/create")
                .then()
                .extract();
        
        assertThat(result.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }
    
    @Test
    public void putProduct() {
        final RequestProductDto requestProductDto = new RequestProductDto("에코", "http://echo", 1000);
        final var result = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestProductDto)
                .when()
                .put("/admin/product/update/1")
                .then()
                .extract();
        
        //then
        final String productLiteral = result.body().asString();
        System.out.println("productLiteral = " + productLiteral);
        final String name = JsonPath.with(productLiteral).getString("name");
        
        assertThat(name).isEqualTo(requestProductDto.getName());
        assertThat(result.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }
    
    @Test
    @DisplayName("상품 삭제 테스트")
    void deleteProductTest() {
        final var deleteResult = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/admin/product/delete/2")
                .then()
                .extract();
        
        assertThat(deleteResult.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
