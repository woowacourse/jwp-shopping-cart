package cart.cartitems;

import cart.cartitems.dao.CartItemDao;
import cart.cartitems.dao.CartItemTestConfig;
import cart.cartitems.dto.CartItemDto;
import cart.cartitems.dto.request.CartItemAddRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import static cart.TestUtils.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;

@Import(CartItemTestConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = "/schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class CartItemApiIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private CartItemTestConfig cartItemTestConfig;

    @Autowired
    private CartItemDao cartItemDao;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        cartItemTestConfig.setMembersAndProducts();
    }

    @Test
    @DisplayName("상품이 정상적으로 추가되었을 때 CREATED 응답 코드를 반환한다")
    void getCartItems() throws JsonProcessingException {
        given()
                .log().all()
                .auth().preemptive().basic(NO_ID_MEMBER2.getEmail(), NO_ID_MEMBER2.getPassword())
                .contentType(ContentType.JSON).body(toJson(new CartItemAddRequest(3L)))
        .when()
               .post("/cart/items")
        .then()
                .log().all().assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .header(HttpHeaders.LOCATION, "/cart/items/3");
    }

    @Test
    @DisplayName("get은 카트에 담긴 모든 아이템을 반환한다")
    void getCartItemsTest() throws JsonProcessingException {
        cartItemDao.saveItemOfMember(new CartItemDto(2, 1));
        cartItemDao.saveItemOfMember(new CartItemDto(2, 3));

        given()
                .log().all()
                .auth().preemptive().basic(NO_ID_MEMBER2.getEmail(), NO_ID_MEMBER2.getPassword())
        .when()
                .get("/cart/items")
        .then()
                .log().all().assertThat()
                .statusCode(HttpStatus.OK.value())
                .body(containsString(NO_ID_PRODUCT1.getName()), containsString(NO_ID_PRODUCT3.getName()));
    }

    @Test
    @DisplayName("상품 삭제가 성공하면 NO_CONTENT 상태 코드를 반환한다.")
    void delete() {
        cartItemDao.saveItemOfMember(new CartItemDto(2, 1));
        cartItemDao.saveItemOfMember(new CartItemDto(2, 3));

        given()
                .log().all()
                .auth().preemptive().basic(NO_ID_MEMBER2.getEmail(), NO_ID_MEMBER2.getPassword())
        .when()
                .delete("/cart/items/" + 3)
        .then()
                .log().all().assertThat()
                .statusCode(HttpStatus.NO_CONTENT.value());

        assertThat(cartItemDao.findProductIdsByMemberId(2)).hasSize(1);
    }

    @Test
    @DisplayName("없는 상품을 삭제하려고 하면 NOT FOUND를 반환한다.")
    void deleteFail() {
        cartItemDao.saveItemOfMember(new CartItemDto(2, 1));
        cartItemDao.saveItemOfMember(new CartItemDto(2, 3));

        given()
                .log().all()
                .auth().preemptive().basic(NO_ID_MEMBER2.getEmail(), NO_ID_MEMBER2.getPassword())
        .when()
                .delete("/cart/items/" + 2)
        .then()
                .log().all().assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());

        assertThat(cartItemDao.findProductIdsByMemberId(2)).hasSize(2);
    }
}
