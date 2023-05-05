package cart.controller;

import cart.dto.CartProductRequest;
import cart.service.CartProductService;
import cart.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Base64;
import java.util.List;

import static cart.fixture.MemberFixture.FIRST_MEMBER;
import static cart.fixture.ProductFixture.FIRST_PRODUCT;
import static cart.fixture.ProductFixture.SECOND_PRODUCT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("NonAsciiCharacters")
@WebMvcTest(CartProductController.class)
class CartProductControllerTest {
    private static final String AUTHORIZATION_HEADER = "Basic " + Base64.getEncoder().encodeToString(("firstMember" + ":" + "password").getBytes());

    @MockBean
    private CartProductService cartProductService;

    @MockBean
    private MemberService memberService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 장바구니의_모든_상품을_조회한다() throws Exception {
        given(cartProductService.findAll(1L)).willReturn(List.of(FIRST_PRODUCT.RESPONSE, SECOND_PRODUCT.RESPONSE));
        given(memberService.findByEmail(any())).willReturn(FIRST_MEMBER.RESPONSE);

        mockMvc.perform(get("/cart-products")
                        .header("Authorization", AUTHORIZATION_HEADER))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    void 장바구니에_상품을_저장한다() throws Exception {
        CartProductRequest cartProductRequest = new CartProductRequest(1L);
        given(cartProductService.save(anyLong(), any())).willReturn(1L);
        given(memberService.findByEmail(any())).willReturn(FIRST_MEMBER.RESPONSE);

        mockMvc.perform(post("/cart-products")
                        .content(objectMapper.writeValueAsString(cartProductRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", AUTHORIZATION_HEADER))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void 장바구니에서_상품을_삭제한다() throws Exception {
        given(memberService.findByEmail(any())).willReturn(FIRST_MEMBER.RESPONSE);

        mockMvc.perform(delete("/cart-products/1")
                        .header("Authorization", AUTHORIZATION_HEADER))
                .andExpect(status().isNoContent());
    }
}
