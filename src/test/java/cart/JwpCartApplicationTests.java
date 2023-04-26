package cart;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

import cart.controller.dto.ItemRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JwpCartApplicationTests {

    @BeforeEach
    void setUp(@LocalServerPort int port) {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("상품을 등록한다.")
    void createItemRequestSuccess() {
        ItemRequest itemRequest = createItemRequest("맥북", "http://image.com", 15_000);

        given()
                .contentType(ContentType.JSON)
                .body(itemRequest)
                .when()
                .post("/items")
                .then().log().all()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.CREATED.value())
                .body("id", greaterThan(0))
                .body("name", is("맥북"))
                .body("imageUrl", is("http://image.com"))
                .body("price", is(15_000));
    }

    @Test
    @Sql(value = {"/truncate.sql", "/insert.sql"})
    @DisplayName("상품 전체를 조회한다.")
    void findAllItemRequestSuccess() {
        when()
                .get("/items")
                .then().log().all()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.OK.value())
                .body("size()", is(7));
    }

    @Test
    @Sql(value = {"/truncate.sql", "/insert.sql"})
    @DisplayName("상품을 변경한다.")
    void updateItemRequestSuccess() {
        ItemRequest itemRequest = createItemRequest("맥북프로", "http://image.com", 35_000);

        given()
                .contentType(ContentType.JSON)
                .body(itemRequest)
                .when()
                .put("/items/{id}", 1L)
                .then().log().all()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.OK.value())
                .body("id", is(1))
                .body("name", is("맥북프로"))
                .body("imageUrl", is("http://image.com"))
                .body("price", is(35_000));
    }

    @Test
    @DisplayName("상품을 삭제한다.")
    void deleteItemRequestSuccess() {
        when()
                .delete("/items/{id}", 1L)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    private static ItemRequest createItemRequest(String name, String imageUrl, int price) {
        return new ItemRequest(name, imageUrl, price);
    }

}
