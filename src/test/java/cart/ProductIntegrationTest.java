package cart;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.controller.dto.ModifyRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.path.xml.element.Node;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ResponseBodyExtractionOptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;

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
        // 2개의 상품 추가
        ModifyRequest request1 = new ModifyRequest("사과", 100, "super.com");
        ModifyRequest request2 = new ModifyRequest("당근", 1000, "super.com");
        String jsonRequest1 = objectMapper.writeValueAsString(request1);
        String jsonRequest2 = objectMapper.writeValueAsString(request2);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(jsonRequest1)
                .when()
                .post("/admin/product");

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(jsonRequest2)
                .when()
                .post("/admin/product");

        // 1개의 상품 수정
        ModifyRequest request3 = new ModifyRequest("애플", 1000, "super.com");
        String jsonRequest3 = objectMapper.writeValueAsString(request3);
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(jsonRequest3)
                .when()
                .put("admin/product/1");

        // 1개의 상품 삭제
        given()
                .when()
                .delete("admin/product/2");

        // 상품 리스트 조회
        ExtractableResponse<Response> result = given().log().all()
                .when()
                .get("/admin")
                .then().log().all()
                .extract();

        // 검증
        ResponseBodyExtractionOptions body = result.body();
        Node strings = body.htmlPath().get();
        assertAll(
                () -> assertThat(strings.toString()).contains("애플"),
                () -> assertThat(strings.toString()).doesNotContain("사과"),
                () -> assertThat(strings.toString()).doesNotContain("당근")
        );

    }
}
