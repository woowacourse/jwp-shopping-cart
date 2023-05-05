package cart.controller;

import cart.dto.MemberResponse;
import cart.dto.ProductRequest;
import cart.dto.ProductResponse;
import cart.service.CartService;
import cart.service.ProductService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners(value = {
        AcceptanceTestExecutionListener.class,}, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
class PageControllerTest {

    @Autowired
    ProductService productService;

    @Autowired
    CartService cartService;

    @Autowired
    PageController pageController;

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void allProducts() {
        // given
        ProductRequest productRequest1 = new ProductRequest("케로로", 1000,
                "https://i.namu.wiki/i/fXDC6tkjS6607gZSXSBdzFq_-12PLPWMcmOddg0dsqRq7Nl30Ek1r23BxxOTiERjGP4eyGmJuVPhxhSpOx2GDw.webp");

        ProductRequest productRequest2 = new ProductRequest("기로로", 2000,
                "https://i.namu.wiki/i/fXDC6tkjS6607gZSXSBdzFq_-12PLPWMcmOddg0dsqRq7Nl30Ek1r23BxxOTiERjGP4eyGmJuVPhxhSpOx2GDw.webp");

        productService.addProduct(productRequest1);
        productService.addProduct(productRequest2);

        Model model = new ExtendedModelMap();

        // when
        String viewName = pageController.allProducts(model);
        List<ProductResponse> products = (List<ProductResponse>) model.getAttribute("products");

        // then
        assertAll(
                () -> assertEquals("index", viewName),
                () -> assertEquals(2, products.size()),
                () -> assertThat(products).extracting("name", "price", "image")
                        .contains(tuple(productRequest1.getName(), productRequest1.getPrice(), productRequest1.getImage())
                                , tuple(productRequest2.getName(), productRequest2.getPrice(), productRequest2.getImage()))
        );
    }

    @Test
    void adminAllProducts() {
        // given
        ProductRequest productRequest1 = new ProductRequest("케로로", 1000,
                "https://i.namu.wiki/i/fXDC6tkjS6607gZSXSBdzFq_-12PLPWMcmOddg0dsqRq7Nl30Ek1r23BxxOTiERjGP4eyGmJuVPhxhSpOx2GDw.webp");

        ProductRequest productRequest2 = new ProductRequest("기로로", 2000,
                "https://i.namu.wiki/i/fXDC6tkjS6607gZSXSBdzFq_-12PLPWMcmOddg0dsqRq7Nl30Ek1r23BxxOTiERjGP4eyGmJuVPhxhSpOx2GDw.webp");

        productService.addProduct(productRequest1);
        productService.addProduct(productRequest2);

        Model model = new ExtendedModelMap();

        // when
        String viewName = pageController.adminAllProducts(model);
        List<ProductResponse> products = (List<ProductResponse>) model.getAttribute("products");

        // then
        assertAll(
                () -> assertEquals("admin", viewName),
                () -> assertEquals(2, products.size()),
                () -> assertThat(products).extracting("name", "price", "image")
                        .contains(tuple(productRequest1.getName(), productRequest1.getPrice(), productRequest1.getImage())
                                , tuple(productRequest2.getName(), productRequest2.getPrice(), productRequest2.getImage()))
        );
    }

    @Test
    void adminAllUsers() {
        Model model = new ExtendedModelMap();

        // when
        String viewName = pageController.adminAllUsers(model);
        List<MemberResponse> members = (List<MemberResponse>) model.getAttribute("members");

        // then
        assertAll(
                () -> assertEquals("settings", viewName),
                () -> assertEquals(0, members.size())
        );
    }

    @Test
    void cartProducts() {
        RestAssured.given().log().all()
                .when().get("/cart")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }
}