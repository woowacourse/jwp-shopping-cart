package cart.controller;

import cart.dao.entity.ProductEntity;
import cart.dto.ResponseProductDto;
import cart.service.CartService;
import cart.service.MemberService;
import cart.service.ProductService;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@WebMvcTest(CartApiController.class)
class CartApiControllerUnitTest {

    private static final String ENCODED_CREDENTIALS = "aHVjaHVAd29vd2FoYW4uY29tOjEyMzQ1NjdhIQ==";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;
    @MockBean
    private CartService cartService;
    @MockBean
    private ProductService productService;

    @Test
    void 장바구니_상품을_읽는다() throws Exception {
        //given
        given(memberService.findIdByEmail(any(String.class)))
                .willReturn(1L);

        given(cartService.findProductIdsByMemberId(any(Long.class)))
                .willReturn(List.of(1L));

        given(productService.findByIds(any()))
                .willReturn(List.of(new ResponseProductDto(new ProductEntity(1L, "치킨", 10_000, "치킨 주소"))));

        //expect
        mockMvc.perform(get("/carts")
                        .header("Authorization", "Basic " + ENCODED_CREDENTIALS)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(equalTo(1)))
                .andExpect(jsonPath("$[0].name").value(equalTo("치킨")))
                .andExpect(jsonPath("$[0].price").value(equalTo(10000)))
                .andExpect(jsonPath("$[0].image").value(equalTo("치킨 주소")));
    }

    @Test
    void 장바구니에_상품을_추가한다() throws Exception {
        //given
        given(memberService.findIdByEmail(any(String.class)))
                .willReturn(1L);

        given(cartService.insert(any(Long.class), any(Long.class)))
                .willReturn(1L);

        //expect
        mockMvc.perform(post("/carts/{id}", 1)
                        .header("Authorization", "Basic " + ENCODED_CREDENTIALS)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void 장바구니에_상품을_제거한다() throws Exception {
        //given
        given(memberService.findIdByEmail(any(String.class)))
                .willReturn(1L);

        given(cartService.delete(any(Long.class), any(Long.class)))
                .willReturn(1);

        //expect
        mockMvc.perform(delete("/carts/{id}", 1)
                        .header("Authorization", "Basic " + ENCODED_CREDENTIALS)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
