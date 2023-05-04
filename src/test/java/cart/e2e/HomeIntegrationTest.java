package cart.e2e;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.controller.dto.ModifyRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.path.xml.XmlPath;
import io.restassured.path.xml.XmlPath.CompatibilityMode;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HomeIntegrationTest {

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
        // 3개의 상품 추가
        ModifyRequest request1 = new ModifyRequest("사과", 100, "super.com");
        ModifyRequest request2 = new ModifyRequest("당근", 1000, "super.com");
        ModifyRequest request3 = new ModifyRequest("포도", 2000, "super.com");
        String jsonRequest1 = objectMapper.writeValueAsString(request1);
        String jsonRequest2 = objectMapper.writeValueAsString(request2);
        String jsonRequest3 = objectMapper.writeValueAsString(request3);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(jsonRequest1)
                .when()
                .post("/products");

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(jsonRequest2)
                .when()
                .post("/products");

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(jsonRequest3)
                .when()
                .post("/products");

        // 1개의 상품 수정
        ModifyRequest request4 = new ModifyRequest("사과즙", 1000, "super.com");
        String jsonRequest4 = objectMapper.writeValueAsString(request4);
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(jsonRequest4)
                .when()
                .put("/products/1");

        // 1개의 상품 삭제
        given()
                .when()
                .delete("/products/2");

        // 상품 리스트 조회
        ExtractableResponse<Response> result2 = get("/")
                .then().log().all()
                .extract();
        Response response = given().log().all()
                .when()
                .get("/");
        // XmlPath로 HTML 내부 정보 Parsing
//        String resultString2 = result2.asString();
        String resultString2 = response.asString();
        XmlPath xmlPath2 = new XmlPath(CompatibilityMode.HTML, resultString2);
        System.out.println(">>>>>>>>>>>>>>");
        System.out.println(xmlPath2.getString(
                "html.body.div"
                        + ".ul.find { it.@class == 'product-grid' }"
                        + ".li[1]"
                        + ".div.find { it.@class == 'product-info' }"
                        + ".div.find { it.@class == 'product-desc' }."
                        + "p.find { it.@class == 'product-name' }"));
        assertAll(
                () -> assertThat(
                        xmlPath2.getString(
                                "html.body.div"
                                        + ".ul.find { it.@class == 'product-grid' }"
                                        + ".li[0]"
                                        + ".div.find { it.@class == 'product-info' }"
                                        + ".div.find { it.@class == 'product-desc' }."
                                        + "p.find { it.@class == 'product-name' }")
                ).contains("사과즙"),
                () -> assertThat(
                        xmlPath2.getString(
                                "html.body.div"
                                        + ".ul.find { it.@class == 'product-grid' }"
                                        + ".li[0]"
                                        + ".div.find { it.@class == 'product-info' }"
                                        + ".div.find { it.@class == 'product-desc' }."
                                        + "p.find { it.@class == 'product-price' }")
                ).contains("1000"),
                () -> assertThat(
                        xmlPath2.getString(
                                "html.body.div"
                                        + ".ul.find { it.@class == 'product-grid' }"
                                        + ".li[1]"
                                        + ".div.find { it.@class == 'product-info' }"
                                        + ".div.find { it.@class == 'product-desc' }."
                                        + "p.find { it.@class == 'product-name' }")
                ).contains("포도")
        );
    }
}
