package cart.controller;

import cart.entity.Product;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("관리자 도구 페이지 접근 테스트")
    void productList() {
        given()
                .when()
                .get("/admin")
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("상품 추가 테스트")
    void createProduct() {
        final Product product = new Product("TEST",
                "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png", new BigDecimal(4000));

        // 추가 요청이 정상적으로 수행되었는가?
        Response response = given()
                .body(product).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/admin/create");

        String location = response.getHeader("location");

        response.then()
                .statusCode(HttpStatus.CREATED.value())
                .header("location", notNullValue());

        // 추가 요청한 데이터가 데이터베이스에 정상적으로 등록되어 있는가?
        assertDatabase(location, product);
    }

    @Test
    @DisplayName("상품 수정 테스트")
    void editProduct() {
        // given : 상품을 저장한다.
        LocationInformation locationInformation = insertProduct();

        // when : 상품을 수정한다.
        Product updateProduct = new Product(locationInformation.getId(), "변경된 무언가", "updatedImage.png", new BigDecimal(9000));
        given()
                .body(updateProduct).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .put("/admin/edit")
                .then().log().all()
                .statusCode(200);

        // then : 상품이 잘 변경되었는지 확인한다.
        assertDatabase(locationInformation.getLocation(), updateProduct);
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    void deleteProduct() {
        // given : 상품을 저장한다.
        LocationInformation locationInformation = insertProduct();

        // when : 상품을 삭제한다.
        given()
                .log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete("/admin/delete/" + locationInformation.getId())
                .then().log().all()
                .statusCode(200);

        // then : 상품이 잘 삭제되었는지 확인한다.
        given().log().all()
                .get(locationInformation.getLocation())
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(equalTo("주어진 정보에 해당하는 데이터를 찾지 못했습니다."));
    }

    @Test
    @DisplayName("유저가 유효하지 않는 입력을 하는 경우 테스트")
    void badRequest() {
        String jsonStr = "{ \"name\":\"홍실\", \"price\":\"321321\", \"imageUrl\":\"ddong.exe\"}";

        given()
                .body(jsonStr).log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/admin/create")
                .then()
                .statusCode(400)
                .body(equalTo("유효한 이미지 확장자가 아닙니다."));
    }

    private LocationInformation insertProduct() {
        final Product product = new Product("TEST787",
                "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png", new BigDecimal(4000));
        Response response = given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(product)
                .when()
                .post("/admin/create");

        String location = response.getHeader("location");
        long productId = Long.parseLong(location
                .replace("/admin/", ""));
        return new LocationInformation(location, productId);
    }

    private static void assertDatabase(String location, Product updateProduct) {
        Response response = given().log().all()
                .get(location);

        response
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo(updateProduct.getName()))
                .body("imageUrl", equalTo(updateProduct.getImageUrl()));

        Integer price = response.getBody().jsonPath().get("price");
        Assertions.assertThat(new BigDecimal(price)).isEqualTo(updateProduct.getPrice());
    }

    private static class LocationInformation {
        private final String location;
        private final long id;

        public LocationInformation(String location, long id) {
            this.location = location;
            this.id = id;
        }

        public String getLocation() {
            return location;
        }

        public long getId() {
            return id;
        }
    }
}
