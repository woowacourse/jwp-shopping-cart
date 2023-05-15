package cart.integration;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ViewIntegrationTest extends IntegrationTest {

    @DisplayName("GET / 요청 시 Status OK 및 HTML 반환")
    @Test
    void shouldResponseHtmlWithStatusOkWhenRequestGetHome() {
        given().log().all()
                .when()
                .get("/")
                .then().log().all()
                .contentType(ContentType.HTML)
                .statusCode(HttpStatus.SC_OK);
    }

    @DisplayName("GET /admin 요청 시 Status OK 및 HTML 반환")
    @Test
    void shouldResponseHtmlWithStatusOkWhenRequestGetToAdmin() {
        given().log().all()
                .when()
                .get("/admin")
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.HTML);
    }

    @DisplayName("GET /settings 요청 시 Status OK 및 HTML 반환")
    @Test
    void shouldResponseHtmlWithStatusOkWhenRequestGetToSettings() {
        final var result = given().log().all()
                .when()
                .get("/settings")
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.HTML)
                .extract();

        assertAll(
                () -> assertThat(result.asString()).contains("user1@woowa.com"),
                () -> assertThat(result.asString()).contains("user2@woowa.com")
        );
    }

}
