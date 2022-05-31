package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.shoppingcart.Fixtures.CUSTOMER_REQUEST_1;
import static woowacourse.shoppingcart.Fixtures.CUSTOMER_REQUEST_2;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowacourse.AcceptanceTest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.EmailDuplicationResponse;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {
    @Test
    @DisplayName("유저 회원가입 성공")
    void joinSuccess() {
        // given
        ExtractableResponse<Response> response = createCustomer();

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(response.header("location")).isNotNull()
        );
    }

    @Test
    @DisplayName("이메일 중복 검사")
    void validateEmailDuplication() {
        // given
        createCustomer();

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/api/validation?email=hudi@gmail.com")
                .then().log().all()
                .extract();

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(
                        response.jsonPath().getObject(".", EmailDuplicationResponse.class).getIsDuplicated()).isTrue()
        );
    }

    @DisplayName("내 정보 조회")
    @Test
    void getMe() {
        // given
        ExtractableResponse<Response> customer = createCustomer();
        String customerUri = customer.header("Location");

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when().get(customerUri)
                .then().log().all().extract();

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.body().jsonPath().getObject(".", CustomerResponse.class))
                        .extracting("email", "profileImageUrl", "name", "gender", "birthDay", "contact", "terms")
                        .containsExactly(CUSTOMER_REQUEST_1.getEmail(), CUSTOMER_REQUEST_1.getProfileImageUrl(),
                                CUSTOMER_REQUEST_1.getName(), CUSTOMER_REQUEST_1.getGender(),
                                CUSTOMER_REQUEST_1.getBirthDay(), CUSTOMER_REQUEST_1.getContact(),
                                CUSTOMER_REQUEST_1.isTerms())
        );

    }

    @DisplayName("내 정보 수정")
    @Test
    void updateMe() {
        // given
        ExtractableResponse<Response> customer = createCustomer();
        String customerUri = customer.header("Location");

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(CUSTOMER_REQUEST_2)
                .when().put(customerUri)
                .then().log().all().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("회원탈퇴")
    @Test
    void deleteMe() {
    }

    private ExtractableResponse<Response> createCustomer() {
        Map<String, Object> params = new HashMap<>();
        params.put("email", CUSTOMER_REQUEST_1.getEmail());
        params.put("password", CUSTOMER_REQUEST_1.getPassword());
        params.put("profileImageUrl", CUSTOMER_REQUEST_1.getProfileImageUrl());
        params.put("name", CUSTOMER_REQUEST_1.getName());
        params.put("gender", CUSTOMER_REQUEST_1.getGender());
        params.put("birthDay", CUSTOMER_REQUEST_1.getBirthDay());
        params.put("contact", CUSTOMER_REQUEST_1.getContact());
        params.put("fullAddress", Map.of("address", CUSTOMER_REQUEST_1.getFullAddress().getAddress(), "detailAddress",
                CUSTOMER_REQUEST_1.getFullAddress().getDetailAddress(), "zoneCode",
                CUSTOMER_REQUEST_1.getFullAddress().getZoneCode()));
        params.put("terms", true);

        //when
        return RestAssured.given().log().all()
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/api/customers")
                .then().log().all()
                .extract();
    }
}
