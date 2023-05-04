package cart;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

import cart.controller.dto.ItemRequest;
import cart.dao.ItemDao;
import cart.domain.Item;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JwpCartApplicationTests {

    @Autowired
    ItemDao itemDao;

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
    @DisplayName("상품 전체를 조회한다.")
    void findAllItemRequestSuccess() {
        when()
                .get("/items")
                .then().log().all()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.OK.value());
    }

    @Test
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
    @DisplayName("존재하지 않는 상품을 변경하면 예외가 발생한다.")
    void updateItemRequestFailWithNotExistsID() {
        ItemRequest itemRequest = createItemRequest("맥북", "http://image.com", 15_000);

        given()
                .contentType(ContentType.JSON)
                .body(itemRequest)
                .when()
                .put("/items/{id}", Long.MAX_VALUE)
                .then().log().all()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", is("일치하는 상품을 찾을 수 없습니다."));
    }

    @Test
    @DisplayName("존재하지 않는 상품을 삭제하면 예외가 발생한다.")
    void deleteItemRequestFailWithNotExistsID() {
        ItemRequest itemRequest = createItemRequest("맥북", "http://image.com", 15_000);

        given()
                .contentType(ContentType.JSON)
                .body(itemRequest)
                .when()
                .delete("/items/{id}", Long.MAX_VALUE)
                .then().log().all()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", is("일치하는 상품을 찾을 수 없습니다."));
    }

    @Test
    @DisplayName("상품을 삭제한다.")
    void deleteItemRequestSuccess() {
        when()
                .delete("/items/{id}", 3L)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("인증 없는 요청이 오는 경우 401 UNAUTHORIZED 예외가 발생한다.")
    void authFailWithUnauthorizedRequest() {
        given().log().all()
                .when().get("/carts")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .body("message", is("인증 정보가 없습니다."));
    }

    @Test
    @DisplayName("회원이 장바구니에 담은 상품을 장바구니에 저장한다.")
    void createCartSuccess() {
        Long savedId = itemDao.insert(new Item("치킨", "chiken@naver.com", 15000));

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", createHeaderValue())
                .when()
                .post("/carts/" + savedId)
                .then().log().all()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("회원의 장바구니에 담겨 있는 상품을 장바구니에 담으면 예외가 발생한다.")
    void createCartFail() {
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", createHeaderValue())
                .when()
                .post("/carts/" + 1)
                .then().log().all()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", is("이미 장바구니에 추가된 상품입니다."));
    }

    @Test
    @DisplayName("회원이 담은 장바구니 목록을 가져온다.")
    void findCarts() {
        given()
                .header("Authorization", createHeaderValue())
                .when()
                .get("/items")
                .then().log().all()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.OK.value());
    }

    private static ItemRequest createItemRequest(String name, String imageUrl, int price) {
        return new ItemRequest(name, imageUrl, price);
    }

    private static String createHeaderValue() {
        String credential = "gray:aaa@aaa.com:helloSpring";
        String encodedCredential = new String(Base64.getEncoder().encode((credential.getBytes())));
        String header = "Basic " + encodedCredential;
        return header;
    }
}
