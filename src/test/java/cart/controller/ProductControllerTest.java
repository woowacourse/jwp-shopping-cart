package cart.controller;

import cart.domain.Product;
import cart.dto.ProductDto;
import cart.repository.ProductRepository;
import cart.service.ProductService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@WebMvcTest
class ProductControllerTest {
    @MockBean
    private ProductService productService;
    
    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.standaloneSetup(new ProductController(productService));
    }
    
    @Test
    void 모든_상품_목록을_가져온다() {
        final ProductDto firstProductDto = new ProductDto(1L, "홍고", "https://ca.slack-edge.com/TFELTJB7V-U04M4NFB5TN-e18b78fabe81-512", 1_000_000_000);
        final ProductDto secondProductDto = new ProductDto(2L, "아벨", "https://ca.slack-edge.com/TFELTJB7V-U04LMNLQ78X-a7ef923d5391-512", 1_000_000_000);
        given(productService.findAll()).willReturn(List.of(firstProductDto, secondProductDto));
        
        RestAssuredMockMvc.given().log().all()
                .when().get("/")
                .then().log().all()
                .status(HttpStatus.OK);
    }
}
