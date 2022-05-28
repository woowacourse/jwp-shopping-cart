package woowacourse.shoppingcart.acceptance;

import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.dto.CustomerCreationRequest;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    @DisplayName("회원가입이 정상적으로 된 경우 상태코드 200을 반환한다.")
    @Test
    void create_right_200() {
        // given
        CustomerCreationRequest request = new CustomerCreationRequest();

        // when
        ValidatableResponse response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/users")
                .then().log().all();

        // then
        response.statusCode(HttpStatus.OK.value());
    }

    @DisplayName("회원 정보 양식이 잘못 되었을 때, 상태코드 400을 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "kun#naver.com:12345667a:쿤aa:이메일 양식이 잘못 되었습니다.",
            "kun@naver.com:1234:쿤aa:비밀번호 양식이 잘못 되었습니다.",
            "kun@naver.com:123456677aa:쿤:닉네임 양식이 잘못 되었습니다."}, delimiter = ':')
    void create_wrongForm_400(String email, String password, String nickname, String message) {
        //given
        CustomerCreationRequest request = new CustomerCreationRequest(email, password, nickname);

        //when
        ValidatableResponse response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/users")
                .then().log().all();

        // then
        response.statusCode(HttpStatus.BAD_REQUEST.value())
                .body("errorCode", equalTo("1000"))
                .body("message", equalTo(message));
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
