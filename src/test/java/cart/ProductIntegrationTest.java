package cart;

import cart.dto.ProductRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductIntegrationTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void indexTest() {
        RestAssured.given()
                .accept(MediaType.TEXT_HTML_VALUE)

                .when()
                .get("/")

                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.HTML)
                .body(containsString("상품목록"));
    }

    @Test
    public void adminTest() {
        RestAssured.given()
                .accept(MediaType.TEXT_HTML_VALUE)

                .when()
                .get("/admin")

                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.HTML)
                .body(containsString("관리자 페이지"));
    }

    @Test
    public void createProducts() {
        String name = "깃짱";
        String imgUrl = "#";
        int price = 10000;

        ProductRequest productRequest = new ProductRequest(name, imgUrl, price);

        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequest)

                .when()
                .post("/admin/products")

                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("name", equalTo(name))
                .body("imgUrl", equalTo(imgUrl))
                .body("price", equalTo(price));
    }

    @Test
    public void updateProductTest() {
        String name = "깃짱";
        String imgUrl = "#";
        int price = 10000;

        ProductRequest productRequest = new ProductRequest(name, imgUrl, price);

        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productRequest)

                .when()
                .put("/admin/products/1")

                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deleteProductTest() {
        RestAssured.given()

                .when()
                .delete("/admin/products/1")

                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
