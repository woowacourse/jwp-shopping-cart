package cart.presentation;

import cart.business.ProductService;
import cart.business.domain.Cart;
import cart.business.domain.Carts;
import cart.business.domain.Member;
import cart.business.domain.Product;
import cart.business.domain.Products;
import cart.presentation.dto.ProductRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class ProductControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private ProductService productService;

    private Products products;
    private Carts carts;

    @BeforeEach
    void setup() {
        initSetting();
        RestAssured.port = port;
    }

    private void initSetting() {
        Product dog = new Product("강아지", "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMzA0MTJfMTM5%2FMDAxNjgxMjYzNTU2NDI2.wlJys88BgEe2MzQrd2k5jjtXsObAZaOM4eidDcM3iLUg.5eE5nUvqLadE0MwlF9c8XLOgqghimMWQU2psfcRuvFYg.PNG.noblecase%2F20230412_102917_5.png&type=a340", 10000);
        Product cat = new Product("고양이", "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMzA0MTBfMjcz%2FMDAxNjgxMTAwOTc5Nzg3.MEOt2vmlKWIlW4PQFfgHPILk0dJxwX42KzrDVu4puSwg.GcSSR6FJWup8Uo1H0xo0_4FuIMhJYJpw6tUmpKP9-Wsg.JPEG.catopia9%2FDSC01276.JPG&type=a340", 20000);
        Product hamster = new Product("햄스터", "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMzA0MjJfMTcx%2FMDAxNjgyMTMzNzQ5MjQ2.DPd6D6NUbKSAOLBVosis9Ptz_lBGkyT4lncgLV0buZUg.KK0-N7fzYAy43jlHd9-4hQJ2CYu7RRqV3UWUi29FQJgg.JPEG.smkh15112%2FIMG_4554.JPG&type=a340", 5000);
        this.products = new Products(new ArrayList<>(List.of(dog, cat, hamster)));

        Member judy = new Member("coding_judith@gmail.com", "judy123");
        Cart cartJudy = new Cart(judy.getId(), new Products(new ArrayList<>()));

        Member teo = new Member("coding_teo@gmail.com", "teo123");
        Cart cartTeo = new Cart(teo.getId(), new Products(new ArrayList<>()));

        carts = new Carts(new ArrayList<>(List.of(cartJudy, cartTeo)));
    }

    @Test
    @Transactional
    @DisplayName("/products로 PUT 요청을 보낼 수 있다")
    void test_update_request() {
        // given
        //when, then
        ProductRequest teo = new ProductRequest("테오", "https://", 1);
        Integer teoId = productService.create(teo);

        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ProductRequest("테오", "https://", 1))
                .when().put("/products/" + teoId)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @Transactional
    @DisplayName("/products로 DELETE 요청을 보낼 수 있다")
    void test_delete_request() {
        // given

        ProductRequest judy = new ProductRequest("주디", "https://", 1);
        Integer judyId = productService.create(judy);

        //when, then
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ProductRequest("주디", "https://", 1))
                .when().delete("/products/" + judyId)
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}
