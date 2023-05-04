package cart.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.common.AuthInfoHandlerMethodArgumentResolver;
import cart.domain.auth.service.AuthService;
import cart.domain.cart.service.CartService;
import cart.dto.MemberInformation;
import cart.dto.CartResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
class CartControllerTest {

    @MockBean
    private AuthService authService;
    @MockBean
    private CartService cartService;
    @MockBean
    private AuthInfoHandlerMethodArgumentResolver authInfoHandlerMethodArgumentResolver;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("장바구니 리스트 api")
    public void testGetCarts() throws Exception {
        //given
        final MemberInformation memberInformation = new MemberInformation("test@test.com", "password");
        when(authInfoHandlerMethodArgumentResolver.resolveArgument(any(), any(), any(), any()))
            .thenReturn(memberInformation);
        final List<CartResponse> cartResponses = List.of(
            new CartResponse(1L, "name1", "imageUrl1", 1000),
            new CartResponse(2L, "name2", "imageUrl2", 2000)
        );

        when(cartService.findByEmail(any()))
            .thenReturn(cartResponses);

        //when
        //then
        final MvcResult mvcResult = mockMvc.perform(get("/cart")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authentication", "Basic sdfksajdfklsdf")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();

        final String responseString = mvcResult.getResponse().getContentAsString();
        final List<CartResponse> result = objectMapper.readValue(responseString,
            new TypeReference<>() {
            });

        for (int i = 0; i < result.size(); i++) {
            assertThat(result.get(i).getId()).isEqualTo(cartResponses.get(i).getId());
        }
    }

    @Test
    @DisplayName("장바구니 추가 api")
    public void testAddCart() throws Exception {
        //given
        final MemberInformation memberInformation = new MemberInformation("test@test.com", "password");
        when(authInfoHandlerMethodArgumentResolver.resolveArgument(any(), any(), any(), any()))
            .thenReturn(memberInformation);

        //when
        //then
        mockMvc.perform(post("/cart?productId=1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authentication", "Basic sdfksajdfklsdf")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("장바구니 삭제 api")
    public void testDeleteCart() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(delete("/cart?id=1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authentication", "Basic sdfksajdfklsdf")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNoContent());
    }
}
