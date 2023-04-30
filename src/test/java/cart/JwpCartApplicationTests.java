package cart;

import static cart.fixture.RequestFactory.ADD_MAC_BOOK_REQUEST;
import static cart.fixture.RequestFactory.UPDATE_MAC_BOOK_REQUEST;
import static cart.fixture.RequestFactory.createAddItemRequest;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

import cart.controller.dto.AddCartRequest;
import cart.controller.dto.AddItemRequest;
import cart.controller.dto.SignInRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
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
        @Sql(value = {"/truncate.sql", "/items.sql"})
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
                    .statusCode(HttpStatus.NOT_FOUND.value())
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
                    .statusCode(HttpStatus.NOT_FOUND.value())
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

    @Nested
    @DisplayName("사용자 로그인 테스트")
    class UserControllerSignInSuccessTest {

        @Test
        @DisplayName("존재하는 사용자 이메일과 비밀번호가 일치하면 로그인에 성공한다.")
        void signInSuccess() {
            SignInRequest signInRequest = new SignInRequest("a@a.com", "a");

            given()
                    .contentType(ContentType.JSON)
                    .body(signInRequest)
                    .when()
                    .post("/users/sign-in")
                    .then().log().all()
                    .contentType(ContentType.JSON)
                    .statusCode(HttpStatus.OK.value())
                    .body("basic", is("YUBhLmNvbTph"));
        }

        @Test
        @DisplayName("존재하지 않는 사용자 이메일이나 비밀번호가 일치하지 않는 경우 로그인에 실패한다.")
        void signInFailWithNotUserOrNotMatchesPassword() {
            SignInRequest signInRequest = new SignInRequest("c@c.com", "d");

            given()
                    .contentType(ContentType.JSON)
                    .body(signInRequest)
                    .when()
                    .post("/users/sign-in")
                    .then().log().all()
                    .contentType(ContentType.JSON)
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message", is("로그인에 실패했습니다."));
        }
    }

    @Nested
    @DisplayName("사용자 로그인 데이터 검증 실패 테스트")
    class UserControllerValidationFailTest {

        @Test
        @DisplayName("이메일이 비어 있으면 BAD REQUEST가 반환된다.")
        void signInFailWithBlankEmail() {
            SignInRequest signInRequest = new SignInRequest("  ", "d");

            given()
                    .contentType(ContentType.JSON)
                    .body(signInRequest)
                    .when()
                    .post("/users/sign-in")
                    .then().log().all()
                    .contentType(ContentType.JSON)
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message", containsString("이메일을 입력해주세요."));
        }

        @Test
        @DisplayName("이메일이 이메일 형식이 아니면 있으면 BAD REQUEST가 반환된다.")
        void signInFailWithNotEmailFormat() {
            SignInRequest signInRequest = new SignInRequest("aaa", "d");

            given()
                    .contentType(ContentType.JSON)
                    .body(signInRequest)
                    .when()
                    .post("/users/sign-in")
                    .then().log().all()
                    .contentType(ContentType.JSON)
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message", containsString("이메일 형식으로 입력해주세요."));
        }

        @Test
        @DisplayName("비밀번호가 비어 있으면 BAD REQUEST가 반환된다.")
        void signInFailWithBlankPassword() {
            SignInRequest signInRequest = new SignInRequest("a@a.com", "     ");

            given()
                    .contentType(ContentType.JSON)
                    .body(signInRequest)
                    .when()
                    .post("/users/sign-in")
                    .then().log().all()
                    .contentType(ContentType.JSON)
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message", containsString("비밀번호를 입력해주세요."));
        }
    }

    @Nested
    @DisplayName("장바구니 CRD 테스트")
    @Sql({"/truncate.sql", "/items.sql", "/carts.sql"})
    class CartControllerCRDSuccessTest {

        private String basic;

        @BeforeEach
        void setUp() {
            SignInRequest signInRequest = new SignInRequest("a@a.com", "a");

            Response response = given()
                    .contentType(ContentType.JSON)
                    .body(signInRequest)
                    .when()
                    .post("/users/sign-in");

            basic = response.jsonPath().get("basic");
        }

        @Test
        @DisplayName("로그인한 사용자의 장바구니를 모두 조회한다.")
        void findAllCartsSuccess() {
            given()
                    .header("Authorization", "Basic " + basic)
                    .when()
                    .get("/carts")
                    .then().log().all()
                    .statusCode(HttpStatus.OK.value())
                    .body("size()", greaterThan(0));
        }

        @Test
        @DisplayName("로그인한 사용자의 장바구니에 상품을 추가한다.")
        void addCartSuccess() {
            AddCartRequest addCartRequest = new AddCartRequest(2L);

            given()
                    .header("Authorization", "Basic " + basic)
                    .contentType(ContentType.JSON)
                    .body(addCartRequest)
                    .when()
                    .post("/carts")
                    .then().log().all()
                    .statusCode(HttpStatus.CREATED.value())
                    .body("cartId", is(2))
                    .body("itemName", is("자전거2"))
                    .body("itemImageUrl", is("https://cdn.imweb.me/thumbnail/20220817/7b35b82e7c1ce.jpg"));
        }

        @Test
        @DisplayName("로그인한 사용자의 장바구니에 상품을 삭제한다.")
        void deleteCartSuccess() {
            given()
                    .header("Authorization", "Basic " + basic)
                    .when()
                    .delete("/carts/{id}", 1L)
                    .then().log().all()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        }

        @Test
        @DisplayName("이미 추가한 상품을 장바구니에 다시 추가하면 BAD REQUEST를 반환한다.")
        void addCartFailWithAlreadyExistsItem() {
            AddCartRequest addCartRequest = new AddCartRequest(1L);

            given()
                    .header("Authorization", "Basic " + basic)
                    .contentType(ContentType.JSON)
                    .body(addCartRequest)
                    .when()
                    .post("/carts")
                    .then().log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("message", is("이미 장바구니에 존재하는 상품입니다."));
        }

        @Test
        @DisplayName("장바구니에 없는 상품을 삭제하려고 하면 NOT FOUND를 반환한다.")
        void deleteCartFailWithNotExistsItem() {
            given()
                    .header("Authorization", "Basic " + basic)
                    .when()
                    .delete("/carts/{id}", -999L)
                    .then().log().all()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("message", is("장바구니에 존재하지 않는 상품입니다."));
        }
    }

    @Nested
    @DisplayName("장바구니 CD 실패 테스트")
    class CartControllerCDFailTest {

        @Test
        @DisplayName("로그인하지 않은 사용자가 장바구니를 조회하면 UNAUTHORIZED를 반환한다.")
        void findAllCartsFailWithNotSignIn() {
            given()
                    .when()
                    .get("/carts")
                    .then().log().all()
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .body("message", is("로그인이 필요한 기능입니다."));
        }

        @Test
        @DisplayName("로그인하지 않은 사용자가 장바구니에 상품을 추가하면 UNAUTHORIZED를 반환한다.")
        void addCartFailWithNotSignIn() {
            AddCartRequest addCartRequest = new AddCartRequest(2L);

            given()
                    .contentType(ContentType.JSON)
                    .body(addCartRequest)
                    .when()
                    .post("/carts")
                    .then().log().all()
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .body("message", is("로그인이 필요한 기능입니다."));
        }

        @Test
        @DisplayName("로그인하지 않은 사용자가 장바구니의 상품을 삭제하면 UNAUTHORIZED를 반환한다.")
        void deleteCartFailWithNotSignIn() {
            given()
                    .when()
                    .delete("/carts/{id}", 1L)
                    .then().log().all()
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .body("message", is("로그인이 필요한 기능입니다."));
        }
    }
}
