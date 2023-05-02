package cart.controller;

import cart.auth.AuthSubjectArgumentResolver;
import cart.cart.dto.CartResponse;
import cart.cart.service.CartService;
import cart.member.dto.MemberRequest;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@WebMvcTest(CartController.class)
class CartControllerTest {
    private static final String DEFAULT_PATH = "/carts/";
    
    @MockBean
    private CartService cartService;
    @MockBean
    private AuthSubjectArgumentResolver resolver;
    
    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.standaloneSetup(
                MockMvcBuilders.standaloneSetup(new CartController(cartService))
                        .setCustomArgumentResolvers(resolver)
        );
    }
    
    @Test
    void 멤버_정보의_인증을_진행한_뒤_장바구니를_저장한다() {
        // given
        String authHeader = "Basic " + Base64.getEncoder().encodeToString(("a@a.com" + ":" + "password1").getBytes());
        given(resolver.supportsParameter(any())).willReturn(true);
        given(resolver.resolveArgument(any(), any(), any(), any())).willReturn(new MemberRequest(1L, "a@a.com", "password1"));
        given(cartService.addCart(anyLong(), any())).willReturn(1L);
        
        // when
        CartResponse cart = RestAssuredMockMvc.given().log().all()
                .header("Authorization", authHeader)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post(DEFAULT_PATH + 1)
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(CartResponse.class);

        // then
        assertAll(
                () -> assertThat(cart).isEqualTo(new CartResponse(1L, 1L, 1L)),
                () -> then(cartService).should(only()).addCart(anyLong(), any())
        );
    }
}
