package cart.controller;

import cart.dao.cart.CartDao;
import cart.dao.item.ItemDao;
import cart.dao.member.MemberDao;
import cart.dto.cart.CartSaveRequest;
import cart.entity.ItemEntity;
import cart.entity.MemberEntity;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.Base64Utils;

import static org.hamcrest.Matchers.equalTo;

@Sql("classpath:schema.sql")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private CartDao cartDao;
    @Autowired
    private ItemDao itemDao;
    @Autowired
    private MemberDao memberDao;

    private String authorization;
    private String invalidAuthorization;

    @BeforeEach
    void setUp() {
        ItemEntity item = itemDao.save(new ItemEntity("치킨", "a", 10000));

        MemberEntity member = memberDao.save(new MemberEntity("email@email.com", "hello", "01012345678", "password"));

        cartDao.save(member.getEmail(), itemDao.findById(item.getId()));

        authorization = Base64Utils.encodeToString((member.getEmail() + ":" + member.getPassword()).getBytes());
        invalidAuthorization = Base64Utils.encodeToString((member.getEmail() + ":" + "abc").getBytes());
    }

    @Test
    @DisplayName("장바구니 추가 테스트")
    void addCart() {
        ItemEntity item = itemDao.save(new ItemEntity("피자", "b", 20000));
        CartSaveRequest request = new CartSaveRequest(item.getId());

        RestAssured.given().log().all()
                .header("Authorization", "Basic " + authorization)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("http://localhost:" + port + "/cart")
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("사용자 패스워드가 일치하지 않은경우 테스트")
    void memberPasswordMismatch() {
        ItemEntity item = itemDao.save(new ItemEntity("피자", "b", 20000));
        CartSaveRequest request = new CartSaveRequest(item.getId());

        RestAssured.given().log().all()
                .header("Authorization", "Basic " + invalidAuthorization)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("http://localhost:" + port + "/cart")
                .then()
                .body("message", equalTo("패스워드가 일치하지 않습니다. 다시 시도해주세요."))
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @DisplayName("유효하지 않은 Authorization 테스트")
    void invalidAuthorization() {
        ItemEntity item = itemDao.save(new ItemEntity("피자", "b", 20000));
        CartSaveRequest request = new CartSaveRequest(item.getId());

        RestAssured.given().log().all()
                .header("Authorization", "Basic " + invalidAuthorization + "error")
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("http://localhost:" + port + "/cart")
                .then()
                .body("message", equalTo("예기치 않은 오류가 발생했습니다."))
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @Test
    @DisplayName("장바구니 삭제 테스트")
    void deleteCart() {
        RestAssured.given().log().all()
                .header("Authorization", "Basic " + authorization)
                .when()
                .delete("http://localhost:" + port + "/cart/" + 1)
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}
