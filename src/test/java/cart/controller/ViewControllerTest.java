package cart.controller;

import cart.auth.AuthSubjectArgumentResolver;
import cart.member.service.MemberService;
import cart.product.service.ProductService;
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

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@WebMvcTest(ViewController.class)
class ViewControllerTest {
    @MockBean
    private ProductService productService;
    @MockBean
    private MemberService memberService;
    @MockBean
    private AuthSubjectArgumentResolver resolver;
    
    @BeforeEach
    void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/templates");
        viewResolver.setSuffix(".html");
        
        RestAssuredMockMvc.standaloneSetup(
                MockMvcBuilders.standaloneSetup(new ViewController(productService, memberService))
                        .setViewResolvers(viewResolver)
        );
    }
    
    @Test
    void 모든_상품_목록을_가져온_후_index_페이지로_이동한다() {
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
    
    @Test
    void 모든_회원_정보를_가져온_후_설정_페이지로_이동한다() {
        // when
        final MvcResult mvcResult = RestAssuredMockMvc.given().log().all()
                .when().get("/settings")
                .then().log().all()
                .assertThat()
                .status(HttpStatus.OK)
                .extract()
                .response()
                .getMvcResult();
        
        final ModelAndView modelAndView = mvcResult.getModelAndView();
        
        // then
        assertAll(
                () -> then(memberService).should(only()).findAll(),
                () -> assertThat(Objects.requireNonNull(modelAndView).getViewName()).isEqualTo("settings")
        );
    }
    
    @Test
    void 장바구니_페이지로_이동한다() {
        // when
        final MvcResult mvcResult = RestAssuredMockMvc.given().log().all()
                .when().get("/cart")
                .then().log().all()
                .assertThat()
                .status(HttpStatus.OK)
                .extract()
                .response()
                .getMvcResult();
        
        final ModelAndView modelAndView = mvcResult.getModelAndView();
        
        // then
        assertThat(Objects.requireNonNull(modelAndView).getViewName()).isEqualTo("cart");
    }
}
