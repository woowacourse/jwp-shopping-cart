package woowacourse.config;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import woowacourse.global.AcceptanceTest;

@DisplayName("CORS 테스트")
public class CorsTest extends AcceptanceTest {

    @DisplayName("Pre-Flight 요청 테스트")
    @Test
    void preflight() {
        String 다른_ORIGIN = "http://127.0.0.1:5500";
        ExtractableResponse<Response> response = Pre_Flight_요청(다른_ORIGIN);

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(response.header("Access-Control-Allow-Origin")).isEqualTo("*"),
                () -> assertThat(response.header("Access-Control-Allow-Methods"))
                        .isEqualTo("GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH"),
                () -> assertThat(response.header("Access-Control-Max-Age")).isEqualTo("3600"),
                () -> assertThat(response.header("Access-Control-Allow-Headers"))
                        .isEqualTo("Origin, X-Requested-With, Content-Type, Accept, Authorization"),
                () -> assertThat(response.header("Access-Control-Allow-Credentials")).isEqualTo("true"),
                () -> assertThat(response.header("Access-Control-Expose-Headers")).isEqualTo("Location")
        );
    }

    private ExtractableResponse<Response> Pre_Flight_요청(String 다른_ORIGIN) {
        return given().log().all()
                .header("Access-Control-Request-Headers", "content-type")
                .header("Access-Control-Request-Method", "POST")
                .header("Origin", 다른_ORIGIN)
                .when()
                .options("http://localhost/api/customers:" + port)
                .then().log().all()
                .extract();
    }
}
