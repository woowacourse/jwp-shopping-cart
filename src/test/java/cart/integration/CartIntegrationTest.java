package cart.integration;

import cart.controller.dto.AddCartRequest;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

class CartIntegrationTest extends IntegrationTest {

    @DisplayName("GET /cart/products 요청시 존재하는 아이디 비밀번호일 시 OK 반환")
    @Test
    void authenticateOk() {
        given().log().all()
                .auth().preemptive().basic("user1@woowa.com", "123456")
                .when()
                .get("/cart/products")
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .assertThat()
                .body("id", hasSize(2))
                .body("productName[1]", is("product2"))
                .body("productPrice[1]", is(100));
    }

    @DisplayName("GET /cart/products 요청시 존재하지 않는 아이디 비밀번호일 시 unauthorized 반환")
    @Test
    void authenticateBad() {
        given().log().all()
                .auth().preemptive().basic("user1@woowa.co", "123456")
                .when()
                .get("/cart/products")
                .then().log().all()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @DisplayName("POST /cart/products 요청 시 성공하면 status Created 반환")
    @Test
    void addCartProductTest() {
        given().log().all()
                .contentType(ContentType.JSON)
                .body(new AddCartRequest(1L))
                .auth().preemptive().basic("user1@woowa.com", "123456")
                .when()
                .post("/cart/products")
                .then().log().all()
                .statusCode(HttpStatus.SC_CREATED)
                .header("Location", "/cart/products/3");
    }

    @DisplayName("DELETE /cart/products/{id} 요청 시 성공하면 status no content 반환")
    @Test
    void deleteCartProductTest() {
        given().log().all()
                .auth().preemptive().basic("user1@woowa.com", "123456")
                .when()
                .delete("/cart/products/1")
                .then().log().all()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

}
