package cart;

import static cart.fixture.RequestFactory.ADD_MAC_BOOK_REQUEST;
import static cart.fixture.RequestFactory.UPDATE_MAC_BOOK_REQUEST;
import static cart.fixture.RequestFactory.createAddItemRequest;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

import cart.controller.dto.AddItemRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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

    @Nested
    @DisplayName("상품 CRUD 성공 테스트")
    class ItemControllerCRUDSuccessTest {

        @Test
        @DisplayName("상품을 등록한다.")
        void createItemRequestSuccess() {
            given()
                    .contentType(ContentType.JSON)
                    .body(ADD_MAC_BOOK_REQUEST)
                    .when()
                    .post("/items")
                    .then().log().all()
                    .contentType(ContentType.JSON)
                    .statusCode(HttpStatus.CREATED.value())
                    .body("id", greaterThan(0))
                    .body("name", is("맥북"))
                    .body("imageUrl", is("http://image.url"))
                    .body("price", is(1_500_000));
        }

        @Nested
        @DisplayName("상품 RUD 성공 테스트")
        @Sql(value = {"/truncate.sql", "/insert.sql"})
        class ItemControllerRUDSuccessTest {

            @Test
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
            @DisplayName("상품을 변경한다.")
            void updateItemRequestSuccess() {
                given()
                        .contentType(ContentType.JSON)
                        .body(UPDATE_MAC_BOOK_REQUEST)
                        .when()
                        .put("/items/{id}", 1L)
                        .then().log().all()
                        .contentType(ContentType.JSON)
                        .statusCode(HttpStatus.OK.value())
                        .body("id", is(1))
                        .body("name", is("맥북"))
                        .body("imageUrl", is("http://image.url"))
                        .body("price", is(1_500_000));
            }

            @Test
            @DisplayName("상품을 삭제한다.")
            void deleteItemRequestSuccess() {
                when()
                        .delete("/items/{id}", 1L)
                        .then().log().all()
                        .statusCode(HttpStatus.NO_CONTENT.value());
            }
        }
    }

    @Nested
    @DisplayName("상품 UD 실패 테스트")
    @Sql("/truncate.sql")
    class ItemControllerUDFailTest {

        @Test
        @DisplayName("존재하지 않는 상품을 변경하면 예외가 발생한다.")
        void updateItemRequestFailWithNotExistsId() {
            given()
                    .contentType(ContentType.JSON)
                    .body(ADD_MAC_BOOK_REQUEST)
                    .when()
                    .put("/items/{id}", 1L)
                    .then().log().all()
                    .contentType(ContentType.JSON)
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message", is("일치하는 상품을 찾을 수 없습니다."));
        }

        @Test
        @DisplayName("존재하지 않는 상품을 삭제하면 예외가 발생한다.")
        void deleteItemRequestFailWithNotExistsId() {
            given()
                    .contentType(ContentType.JSON)
                    .body(ADD_MAC_BOOK_REQUEST)
                    .when()
                    .delete("/items/{id}", 1L)
                    .then().log().all()
                    .contentType(ContentType.JSON)
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message", is("일치하는 상품을 찾을 수 없습니다."));
        }
    }

    @Nested
    @DisplayName("상품 CRUD 요청 데이터 검증 실패 테스트")
    class ItemControllerValidationFailTest {

        @ParameterizedTest
        @ValueSource(strings = {"", " "})
        @DisplayName("상품 요청 이름에 공백이 입력되면 예외가 발생한다.")
        void createItemRequestFailWithBlankName(String name) {
            AddItemRequest addItemRequest = createAddItemRequest(name, "http://image.com", 15_000);

            given()
                    .contentType(ContentType.JSON)
                    .body(addItemRequest)
                    .when()
                    .post("/items")
                    .then().log().all()
                    .contentType(ContentType.JSON)
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message", is("이름에 공백이 입력될 수 없습니다."));
        }

        @ParameterizedTest
        @ValueSource(strings = {"", " "})
        @DisplayName("상품 요청 이미지 주소에 공백이 입력되면 BAD REQUEST가 반횐된다.")
        void createItemRequestFailWithBlankUrl(String imageUrl) {
            AddItemRequest addItemRequest = createAddItemRequest("맥북", imageUrl, 15_000);

            given()
                    .contentType(ContentType.JSON)
                    .body(addItemRequest)
                    .when()
                    .post("/items")
                    .then().log().all()
                    .contentType(ContentType.JSON)
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message", is("이미지 URL은 공백이 입력될 수 없습니다."));
        }

        @ParameterizedTest
        @ValueSource(ints = {-1, 0})
        @DisplayName("상품 요청 가격에 양수가 아니면 BAD REQUEST가 반횐된다.")
        void createItemRequestFailWithNonPositivePrice(int price) {
            AddItemRequest addItemRequest = createAddItemRequest("맥북", "http://image.com", price);

            given()
                    .contentType(ContentType.JSON)
                    .body(addItemRequest)
                    .when()
                    .post("/items")
                    .then().log().all()
                    .contentType(ContentType.JSON)
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message", is("가격은 양수만 입력할 수 있습니다."));
        }

        @Test
        @DisplayName("상품 수정 시 @PathVariable에 공백을 입력하면 예외가 발생한다.")
        void updateItemRequestFailWithBlankPathVariable() {
            given()
                    .contentType(ContentType.JSON)
                    .body(UPDATE_MAC_BOOK_REQUEST)
                    .when()
                    .put("/items/{id}", "   ")
                    .then().log().all()
                    .contentType(ContentType.JSON)
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message", is("존재하지 않는 상품입니다."));
        }

        @Test
        @DisplayName("상품 수정 시 @PathVariable에 문자열을 입력하면 예외가 발생한다.")
        void updateItemRequestFailWithStringPathVariable() {
            given()
                    .contentType(ContentType.JSON)
                    .body(UPDATE_MAC_BOOK_REQUEST)
                    .when()
                    .put("/items/{id}", "abc")
                    .then().log().all()
                    .contentType(ContentType.JSON)
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message", is("잘못된 경로입니다."));
        }

        @Test
        @DisplayName("상품 삭제 시 @PathVariable에 공백을 입력하면 예외가 발생한다.")
        void deleteItemRequestFailWithBlankPathVariable() {
            given()
                    .when()
                    .delete("/items/{id}", "   ")
                    .then().log().all()
                    .contentType(ContentType.JSON)
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message", is("존재하지 않는 상품입니다."));
        }

        @Test
        @DisplayName("상품 삭제 시 @PathVariable에 문자열을 입력하면 예외가 발생한다.")
        void deleteItemRequestFailWithStringPathVariable() {
            given()
                    .when()
                    .delete("/items/{id}", "abc")
                    .then().log().all()
                    .contentType(ContentType.JSON)
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message", is("잘못된 경로입니다."));
        }
    }
}
