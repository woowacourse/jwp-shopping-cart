package woowacourse.shoppingcart.acceptance;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.dto.CustomerRequest;

import static org.hamcrest.Matchers.equalTo;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {
    @DisplayName("회원가입을 성공적으로 진행한다.")
    @Test
    void addCustomer() {
        CustomerRequest customerRequest =
                new CustomerRequest("forky", "복희", "forky123#", 26);
        RestAssured.given().log().all()
                .body(customerRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/customers/signup")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .body("userName", equalTo("forky"))
                .body("nickName", equalTo("복희"));
    }

    @DisplayName("내 정보 조회")
    @Test
    void getMe() {

    }

    @DisplayName("내 정보 수정")
    @Test
    void updateMe() {
    }

    @DisplayName("회원탈퇴")
    @Test
    void deleteMe() {
    }
}
