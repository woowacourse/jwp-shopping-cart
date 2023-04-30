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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@WebMvcTest(ViewController.class)
class ViewControllerTest {
    @MockBean
    private ProductService productService;
    
    @BeforeEach
    void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/templates");
        viewResolver.setSuffix(".html");
        
        RestAssuredMockMvc.standaloneSetup(
                MockMvcBuilders.standaloneSetup(new ViewController(productService))
                        .setViewResolvers(viewResolver)
        );
    }
    
    @Test
    void 모든_상품_목록을_가져온_후_index_페이지로_이동한다() {
        // given
        final ProductResponse firstProductResponse = new ProductResponse(1L, "홍고", "aa", 1_000_000_000);
        final ProductResponse secondProductResponse = new ProductResponse(2L, "아벨", "bb", 1_000_000_000);
        given(productService.findAll()).willReturn(List.of(firstProductResponse, secondProductResponse));
        
        // when
        final MvcResult mvcResult = RestAssuredMockMvc.given().log().all()
                .when().get("/")
                .then().log().all()
                .assertThat()
                .status(HttpStatus.OK)
                .extract()
                .response()
                .getMvcResult();
        
        final ModelAndView modelAndView = mvcResult.getModelAndView();
        
        // then
        assertAll(
                () -> then(productService).should(only()).findAll(),
                () -> assertThat(Objects.requireNonNull(modelAndView).getViewName()).isEqualTo("index")
        );
    }
    
    @Test
    void 모든_상품_목록을_가져온_후_관리자_페이지로_이동한다() {
        // when
        final MvcResult mvcResult = RestAssuredMockMvc.given().log().all()
                .when().get("/admin")
                .then().log().all()
                .assertThat()
                .status(HttpStatus.OK)
                .extract()
                .response()
                .getMvcResult();
        
        final ModelAndView modelAndView = mvcResult.getModelAndView();
        
        // then
        assertAll(
                () -> then(productService).should(only()).findAll(),
                () -> assertThat(Objects.requireNonNull(modelAndView).getViewName()).isEqualTo("admin")
        );
    }
}
