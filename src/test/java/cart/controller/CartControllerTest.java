package cart.controller;

import cart.auth.AuthSubjectArgumentResolver;
import cart.cart.dto.CartProductResponse;
import cart.cart.dto.CartResponse;
import cart.cart.service.CartService;
import cart.member.dto.MemberRequest;
import cart.product.dto.ProductResponse;
import cart.product.service.ProductService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Base64;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.only;

@SuppressWarnings("NonAsciiCharacters")
@WebMvcTest(CartController.class)
class CartControllerTest {
    private static final String DEFAULT_PATH = "/carts/";
    private static final String AUTH_HEADER = "Basic " + Base64.getEncoder().encodeToString(("a@a.com" + ":" + "password1").getBytes());
    
    @MockBean
    private CartService cartService;
    @MockBean
    private ProductService productService;
    @MockBean
    private AuthSubjectArgumentResolver resolver;
    
    private InOrder inOrder;
    
    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.standaloneSetup(
                MockMvcBuilders.standaloneSetup(new CartController(cartService, productService))
                        .setCustomArgumentResolvers(resolver)
        );
        
        inOrder = inOrder(cartService, productService);
    }
    
    @Test
    void 멤버_정보의_인증을_진행한_뒤_장바구니를_저장한다() {
        // given
        given(resolver.supportsParameter(any())).willReturn(true);
        given(resolver.resolveArgument(any(), any(), any(), any())).willReturn(new MemberRequest(1L, "a@a.com", "password1"));
        given(cartService.addCart(anyLong(), any())).willReturn(1L);
        
        // when
        CartResponse cart = RestAssuredMockMvc.given().log().all()
                .header("Authorization", AUTH_HEADER)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post(DEFAULT_PATH + 1)
                .then().log().all()
                .status(HttpStatus.CREATED)
                .extract()
                .as(CartResponse.class);

        // then
        assertAll(
                () -> assertThat(cart).isEqualTo(new CartResponse(1L, 1L, 1L)),
                () -> then(cartService).should(only()).addCart(anyLong(), any())
        );
    }
    
    @Test
    void MemberRequest를_전달하면_장바구니_상품들을_가져온다() {
        // given
        final ProductResponse firstProduct = new ProductResponse(2L, "product2", "b.com", 2000);
        final ProductResponse secondProduct = new ProductResponse(3L, "product3", "c.com", 3000);
        final CartResponse firstCartResponse = new CartResponse(2L, 1L, 1L);
        final CartResponse secondCartResponse = new CartResponse(4L, 1L, 2L);
        given(productService.findByProductIds(anyList())).willReturn(List.of(firstProduct, secondProduct));
        given(cartService.findByMemberRequest(any())).willReturn(List.of(firstCartResponse, secondCartResponse));
        
        // when
        final List<CartProductResponse> products = RestAssuredMockMvc.given().log().all()
                .header("Authorization", AUTH_HEADER)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get(DEFAULT_PATH)
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().jsonPath().getList("", CartProductResponse.class);
        
        // then
        final CartProductResponse expectFirstCartProduct = CartProductResponse.from(2L, firstProduct);
        final CartProductResponse expectSecondCartProduct = CartProductResponse.from(4L, secondProduct);
        assertAll(
                () -> assertThat(products).containsExactly(expectFirstCartProduct, expectSecondCartProduct),
                () -> then(cartService).should(inOrder).findByMemberRequest(any()),
                () -> then(productService).should(inOrder).findByProductIds(anyList())
        );
    }
    
    @Test
    void carId와_memberId를_전달하면_해당_카트_품목을_삭제한다() {
        // expect
        RestAssuredMockMvc.given().log().all()
                .header("Authorization", AUTH_HEADER)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete(DEFAULT_PATH + 1)
                .then().log().all()
                .assertThat()
                .status(HttpStatus.NO_CONTENT);
        
        then(cartService).should(only()).deleteByCartIdAndMemberId(any(), any());
    }
}
