package cart.e2e;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.controller.dto.ProductSaveRequest;
import cart.controller.dto.ProductUpdateRequest;
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
public class Admin_페이지_테스트 {

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
    public void 상품_추가_수정_삭제_테스트() throws JsonProcessingException {
        상품_저장("사과", 100, "super.com"); // 상품 아이디 1 생성
        상품_저장("당근", 1000, "super.com"); // 상품 아이디 2 생성
        상품_저장("포도", 2000, "super.com"); // 상품 아이디 3 생성

        상품_수정(1, "사과즙", 1000, "super.com");

        상품_삭제(2);

        XmlPath Admin_페이지_정보 = 페이지_정보_가져오기("/admin");
        assertAll(
                () -> assertThat(첫번째_상품의_이름_from(Admin_페이지_정보)).isEqualTo("사과즙"),
                () -> assertThat(첫번째_상품의_가격_from(Admin_페이지_정보)).isEqualTo("1000"),
                () -> assertThat(두번째_상품의_이름_from(Admin_페이지_정보)).isEqualTo("포도")
        );
    }

    private void 상품_저장(String 이름, int 가격, String 이미지_주소) throws JsonProcessingException {
        ProductSaveRequest 상품_저장_요청 = new ProductSaveRequest(이름, 가격, 이미지_주소);
        String JSON_형태의_상품_저장_요청 = objectMapper.writeValueAsString(상품_저장_요청);
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(JSON_형태의_상품_저장_요청)
                .when()
                .post("/products");
    }

    private void 상품_수정(int 상품_아이디, String 이름, int 가격, String 이미지_주소) throws JsonProcessingException {
        ProductUpdateRequest 상품_수정_요청 = new ProductUpdateRequest(이름, 가격, 이미지_주소);
        String JSON_형태의_상품_수정_요청 = objectMapper.writeValueAsString(상품_수정_요청);
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(JSON_형태의_상품_수정_요청)
                .when()
                .put("/products/" + 상품_아이디);
    }

    private void 상품_삭제(int 상품_아이디) {
        given()
                .when()
                .delete("/products/" + 상품_아이디);
    }

    private XmlPath 페이지_정보_가져오기(String 엔드포인트) {
        ExtractableResponse<Response> Admin_페이지_호출_결과 = given().log().all()
                .when()
                .get(엔드포인트)
                .then().log().all()
                .extract();

        String 호출_결과_텍스트 = Admin_페이지_호출_결과.asString();
        XmlPath XML_형식의_결과 = new XmlPath(CompatibilityMode.HTML, 호출_결과_텍스트);
        return XML_형식의_결과;
    }

    private String 첫번째_상품의_이름_from(XmlPath Admin_페이지_정보) {
        return Admin_페이지_정보.getString("html.body.div.table.tbody.tr[0].td[1]");
    }

    private String 첫번째_상품의_가격_from(XmlPath Admin_페이지_정보) {
        return Admin_페이지_정보.getString("html.body.div.table.tbody.tr[0].td[2]");
    }

    private String 두번째_상품의_이름_from(XmlPath Admin_페이지_정보) {
        return Admin_페이지_정보.getString("html.body.div.table.tbody.tr[1].td[1]");
    }
}
