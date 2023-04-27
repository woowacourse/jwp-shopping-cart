package cart.controller;

import cart.service.ProductService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@WebMvcTest(AdminController.class)
class AdminControllerTest {
    @MockBean
    private ProductService productService;
    
    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.standaloneSetup(new AdminController(productService));
    }
    
    @Test
    void 상품을_생성한다() {
        RestAssuredMockMvc.given().log().all()
                .when().post("/admin")
                .then().log().all();
    }
    
    @Test
    void 상품을_수정한다() {
        RestAssuredMockMvc.given().log().all()
                .when().put("/admin/1")
                .then().log().all();
    }
    
    @Test
    void 상품을_삭제한다() {
        RestAssuredMockMvc.given().log().all()
                .when().delete("/admin/1")
                .then().log().all();
    }
}
