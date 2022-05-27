package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.dto.CustomerSignUpRequest;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    @DisplayName("회원가입")
    @Nested
    class AddCustomer extends AcceptanceTest {

        private CustomerSignUpRequest request;

        @BeforeEach
            void prepare() {
                request = new CustomerSignUpRequest("username", "password123", "01012345678", "성담빌딩");
        }

        @Test
        @DisplayName("성공한다.")
        void success() {
            ExtractableResponse<Response> response = 회원_가입_요청(request);
            회원_가입됨(response);
        }

        @Test
        @DisplayName("중복된 유저 이름으로 가입하여 실패")
        void uplicatedUsername_fail() {
            회원_가입_요청(request);

            ExtractableResponse<Response> response = 회원_가입_요청(request);
            회원_가입_실패(response);
        }
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

    public static ExtractableResponse<Response> 회원_가입_요청(final CustomerSignUpRequest request) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/api/customers/signup")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 내_정보_조회(final String accessToken) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .when().get("/api/customers")
                .then().log().all()
                .extract();
    }

    private void 회원_가입됨(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    private void 회원_가입_실패(final ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
