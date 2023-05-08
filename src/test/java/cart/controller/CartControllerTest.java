package cart.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.auth.AuthInfoArgumentResolver;
import cart.auth.AuthService;
import cart.auth.AuthenticationCheckInterceptor;
import cart.domain.cart.dto.CartDto;
import cart.dto.CartResponse;
import cart.domain.cart.service.CartService;
import cart.domain.member.dto.MemberDto;
import cart.domain.product.dto.ProductDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
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
    private AuthInfoArgumentResolver authInfoArgumentResolver;
    @MockBean
    private AuthenticationCheckInterceptor authenticationCheckInterceptor;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("장바구니 리스트 api")
    public void testGetCarts() throws Exception {
        //given
        final MemberDto memberDto1 = new MemberDto("test1@test.com", "password1");
        final MemberDto memberDto2 = new MemberDto("test2@test.com", "password2");
        final ProductDto productDto1 = new ProductDto(1L, "test1", 1000, "imageUrl1",
            LocalDateTime.now(),
            LocalDateTime.now());
        final ProductDto productDto2 = new ProductDto(2L, "test2", 2000, "imageUrl2",
            LocalDateTime.now(),
            LocalDateTime.now());
        final List<CartDto> cartDtos = List.of(
            new CartDto(1L, productDto1, memberDto1, LocalDateTime.now(), LocalDateTime.now()),
            new CartDto(2L, productDto2, memberDto2, LocalDateTime.now(), LocalDateTime.now())
            );

        when(authenticationCheckInterceptor.preHandle(any(), any(), any()))
            .thenReturn(true);
        when(authInfoArgumentResolver.resolveArgument(any(), any(), any(), any()))
            .thenReturn(memberDto1);
        when(cartService.findByEmail(any()))
            .thenReturn(cartDtos);

        //when
        //then
        final MvcResult mvcResult = mockMvc.perform(get("/api/cart")
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
            assertThat(result.get(i).getId()).isEqualTo(cartDtos.get(i).getId());
        }
    }

    @Test
    @DisplayName("장바구니 추가 api")
    public void testAddCart() throws Exception {
        //given
        final MemberDto memberDto = new MemberDto("test@test.com", "password");
        when(authInfoArgumentResolver.resolveArgument(any(), any(), any(), any()))
            .thenReturn(memberDto);
        when(authenticationCheckInterceptor.preHandle(any(), any(), any()))
            .thenReturn(true);

        //when
        //then
        mockMvc.perform(post("/api/cart?productId=1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authentication", "Basic sdfksajdfklsdf")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("장바구니 삭제 api")
    public void testDeleteCart() throws Exception {
        //given
        when(authenticationCheckInterceptor.preHandle(any(), any(), any()))
            .thenReturn(true);

        //when
        //then
        mockMvc.perform(delete("/api/cart?id=1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authentication", "Basic sdfksajdfklsdf")
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNoContent());
    }
}
