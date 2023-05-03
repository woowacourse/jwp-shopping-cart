package cart;

import cart.controller.dto.ModifyRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Sql("/test.sql")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductIntegrationTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("2개의 상품 추가 후 1개 수정, 1개 삭제하는 시나리오 검증")
    @Test
    public void getProducts() throws JsonProcessingException {
        // 1개의 상품 수정
        ModifyRequest request3 = new ModifyRequest("애플", 1000, "super.com");
        String jsonRequest3 = objectMapper.writeValueAsString(request3);
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(jsonRequest3)
                .when()
                .patch("/product/1");

        // 1개의 상품 삭제
        given()
                .when()
                .delete("/product/2");

        // 상품 리스트 조회
        ExtractableResponse<Response> result = given().log().all()
                .when()
                .get("/admin")
                .then().log().all()
                .extract();

        // 검증

        final String html = result.asString();
        final XmlPath xmlPath = new XmlPath(XmlPath.CompatibilityMode.HTML, html);
        final String expectApple = xmlPath.getString("html.body.div.table.tbody.tr[0].td[1]");
        final String expectApplePrice = xmlPath.getString("html.body.div.table.tbody.tr[0].td[2]");
        final String expectNull = xmlPath.getString("html.body.div.table.tbody.tr[1].td[1]");

        assertAll(
                () -> assertThat(expectApple).isEqualTo("애플"),
                () -> assertThat(expectApplePrice).isEqualTo("1000"),
                () -> assertThat(expectNull).isNullOrEmpty()
        );
    }
}
