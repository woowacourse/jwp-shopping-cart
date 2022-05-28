package woowacourse.shoppingcart.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;

@DisplayName("회원 관련 기능")
public class CustomerAcceptanceTest extends AcceptanceTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
    }

    @DisplayName("회원가입")
    @Test
    void addCustomer() {
        final String requestUrl = createCustomer();
        final CustomerResponse expectedCustomerResponse = new CustomerResponse(
                "example@example.com", "http://gravatar.com/avatar/1?d=identicon",
                "name", "male", "1998-08-07", "12345678910", "address", "detailAddress", "12345"
        );

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .get(requestUrl)
                .then().log().all()
                .extract();
        final CustomerResponse customerResponse = response.body()
                .jsonPath()
                .getObject("", CustomerResponse.class);

        assertThat(customerResponse).usingRecursiveComparison()
                .isEqualTo(expectedCustomerResponse);
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

    private String createCustomer() {
        final CustomerRequest customerRequest = new CustomerRequest(
                "example@example.com", "example123!", "http://gravatar.com/avatar/1?d=identicon",
                "name", "male", "1998-08-07", "12345678910", "address", "detailAddress", "12345", true
        );
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(customerRequest)
                .post("/api/users")
                .then().log().all()
                .extract();
        return response.header("location");
    }
}
