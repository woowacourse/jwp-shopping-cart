package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import woowacourse.shoppingcart.dto.CustomerRequest;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {
    @DisplayName("회원가입")
    @Test
    void addCustomer() {
        // given
        CustomerRequest customerRequest = new CustomerRequest("dongho108", "ehdgh1234", "01022728572", "인천 서구 검단로");

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
            .body(customerRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/api/members/signup")
            .then().log().all()
            .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    // @DisplayName("회원가입 시 username은 3자 이상 ~ 15자 이하로만 이루어져 있어야 된다.")
    // @Test
    // void validateUsernameLength() {
    //     // given
    //     CustomerRequest customerRequest = new CustomerRequest("do", "ehdgh1234", "01022728572", "인천 서구 검단로 851 동부아파트 108동 303호");
    //
    //     // when
    //     ExtractableResponse<Response> response = RestAssured.given().log().all()
    //         .body(customerRequest)
    //         .contentType(MediaType.APPLICATION_JSON_VALUE)
    //         .when()
    //         .post("/api/members/signup")
    //         .then().log().all()
    //         .extract();
    //
    //     // then
    //     assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    // }

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
