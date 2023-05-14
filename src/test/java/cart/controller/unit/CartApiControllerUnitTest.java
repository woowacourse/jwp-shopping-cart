package cart.controller.unit;

import cart.controller.CartApiController;
import cart.controller.authentication.AuthenticationInterceptor;
import cart.domain.ProductEntity;
import cart.dto.ResponseProductDto;
import cart.service.CartService;
import cart.service.MemberService;
import cart.service.ProductService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@WebMvcTest(CartApiController.class)
class CartApiControllerUnitTest {

    private static final String ENCODED_CREDENTIALS = "aHVjaHVAd29vd2FoYW4uY29tOjEyMzQ1NjdhIQ==";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;
    @MockBean
    private CartService cartService;
    @MockBean
    private ProductService productService;
    @MockBean
    private AuthenticationInterceptor authenticationInterceptor;

    @Test
    void 장바구니_상품을_읽는다() throws Exception {
        //given
        given(authenticationInterceptor.preHandle(any(), any(), any()))
                .willReturn(true);

        given(cartService.findCartProducts(any()))
                .willReturn(List.of(new ResponseProductDto(new ProductEntity(1L, "chicken", 10_000, "chicken image url"))));

        //when
        final String responseJson = mockMvc.perform(get("/carts")
                        .header("Authorization", "Basic " + ENCODED_CREDENTIALS)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        final List<ResponseProductDto> productDtos = objectMapper.readValue(responseJson, new TypeReference<>() {
        });

        //then
        assertSoftly(softly -> {
            softly.assertThat(productDtos).hasSize(1);
            final ResponseProductDto productDto = productDtos.get(0);
            softly.assertThat(productDto.getId()).isEqualTo(1L);
            softly.assertThat(productDto.getName()).isEqualTo("chicken");
            softly.assertThat(productDto.getPrice()).isEqualTo(10_000);
            softly.assertThat(productDto.getImage()).isEqualTo("chicken image url");
        });
    }

    @Test
    void 장바구니에_상품을_추가한다() throws Exception {
        mockMvc.perform(post("/carts/{id}", 1)
                        .header("Authorization", "Basic " + ENCODED_CREDENTIALS)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 장바구니에_상품을_제거한다() throws Exception {
        mockMvc.perform(delete("/carts/{id}", 1)
                        .header("Authorization", "Basic " + ENCODED_CREDENTIALS)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
