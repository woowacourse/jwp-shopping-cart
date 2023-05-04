package cart.controller;

import cart.dto.ProductResponse;
import cart.service.ProductService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;

import static org.mockito.BDDMockito.given;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @MockBean
    private ProductService productService;
    
    @BeforeEach
    void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setSuffix(".html");
        RestAssuredMockMvc.standaloneSetup(
                MockMvcBuilders.standaloneSetup(new ProductController(productService))
                        .setViewResolvers(viewResolver)
        );
    }

    @Test
    void 상품을_조회한다() {
        final ProductResponse firstProductResponse = new ProductResponse(1L, "홍고", "https://ca.slack-edge.com/TFELTJB7V-U04M4NFB5TN-e18b78fabe81-512", 1_000_000_000);
        final ProductResponse secondProductResponse = new ProductResponse(2L, "아벨", "https://ca.slack-edge.com/TFELTJB7V-U04LMNLQ78X-a7ef923d5391-512", 1_000_000_000);
        given(productService.findAll()).willReturn(List.of(firstProductResponse, secondProductResponse));

        RestAssuredMockMvc.given().log().all()
                .when().get("/admin")
                .then().log().all()
                .status(HttpStatus.OK);
    }

    @Test
    void 상품을_생성한다() {
        RestAssuredMockMvc.given().log().all()
                .when().post("/products")
                .then().log().all();
    }
    
    @Test
    void 상품을_수정한다() {
        RestAssuredMockMvc.given().log().all()
                .when().put("/products/1")
                .then().log().all();
    }
    
    @Test
    void 상품을_삭제한다() {
        RestAssuredMockMvc.given().log().all()
                .when().delete("/products/1")
                .then().log().all();
    }
}
