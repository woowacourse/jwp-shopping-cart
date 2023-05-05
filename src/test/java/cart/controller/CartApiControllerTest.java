package cart.controller;

import static org.assertj.core.api.Assertions.assertThat;

import cart.controller.dto.request.cart.CartInsertRequest;
import cart.dao.CartDao;
import cart.dao.CartEntity;
import cart.dao.ProductDao;
import cart.dao.ProductEntity;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import java.util.List;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;

@DisplayName("장바구니 api 테스트")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CartApiControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        jdbcTemplate.execute("DELETE FROM cart");
    }

    @Autowired
    CartDao cartDao;

    @Autowired
    ProductDao productDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @DisplayName("장바구니 조회 테스트")
    @Test
    void showCart() {
        String credentials = "1:naver.com:1234";
        Header header = getHeader(credentials);

        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(header)
                .when().get("http://localhost:" + port + "/carts")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("장바구니 등록 테스트")
    @Test
    void addCart() {
        String credentials = "1:naver.com:1234";
        Header header = getHeader(credentials);

        List<ProductEntity> allProducts = productDao.findAll();
        ProductEntity productEntity = allProducts.get(0);
        int productId = productEntity.getId();
        CartInsertRequest cartInsertRequest = new CartInsertRequest(productId);

        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(header)
                .body(cartInsertRequest)
                .when().post("http://localhost:" + port + "/carts")
                .then()
                .statusCode(HttpStatus.CREATED.value());

        List<CartEntity> findCartByMemberId = cartDao.findCartByMemberId(1);

        assertThat(findCartByMemberId).hasSize(1);
        assertThat(findCartByMemberId.get(0).getProductId()).isEqualTo(1);
    }

    @DisplayName("장바구니 삭제 테스트")
    @Test
    void deleteCart() {
        String credentials = "1:naver.com:1234";
        Header header = getHeader(credentials);

        List<ProductEntity> allProducts = productDao.findAll();
        ProductEntity productEntity = allProducts.get(0);
        int productId = productEntity.getId();
        CartInsertRequest cartInsertRequest = new CartInsertRequest(productId);

        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(header)
                .body(cartInsertRequest)
                .when().post("http://localhost:" + port + "/carts")
                .then()
                .statusCode(HttpStatus.CREATED.value());

        CartEntity cartEntity = cartDao.findCartByMemberId(1).get(0);
        Integer insertCartId = cartEntity.getId();

        System.out.println("insertCartId = " + insertCartId);

        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(header)
                .when().delete("http://localhost:" + port + "/carts/" + insertCartId)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        List<CartEntity> findCartByMemberId = cartDao.findCartByMemberId(1);

        assertThat(findCartByMemberId).hasSize(0);
    }

    private Header getHeader(final String credentials) {
        String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));
        String basicCredentials = "Basic " + encodedCredentials;
        return new Header("Authorization", basicCredentials);
    }

}