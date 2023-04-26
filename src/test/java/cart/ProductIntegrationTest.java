//package cart;
//
//import static io.restassured.RestAssured.given;
//import static org.assertj.core.api.Assertions.assertThat;
//
//import io.restassured.RestAssured;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.DisplayNameGeneration;
//import org.junit.jupiter.api.DisplayNameGenerator;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//
//@SuppressWarnings("NonAsciiCharacters")
//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
//@DisplayName(" 은")
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class ProductIntegrationTest {
//
//    @LocalServerPort
//    private int port;
//
//    @BeforeEach
//    void setUp() {
//        RestAssured.port = port;
//    }
//
//    @Test
//    public void getProducts() {
//        var result = given()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .when()
//                .get("/products")
//                .then()
//                .extract();
//
//        assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
//    }
//
//    @Test
//    void 상품_저장_테스트() {
//        // given
//        given().log().all()
//                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
//                .multiPart("name", "이름")
//                .multiPart("imageUrl", "이미지")
//                .multiPart("price", 1000)
//                .post("/products")
//                .then()
//                .log().all()
//                .statusCode(201);
//
//        // when
//
//        // then
//    }
//}
