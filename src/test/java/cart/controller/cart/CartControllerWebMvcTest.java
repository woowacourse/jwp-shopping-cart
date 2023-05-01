package cart.controller.cart;

import cart.config.auth.LoginMemberArgumentResolver;
import cart.controller.CartController;
import cart.domain.member.Member;
import cart.dto.member.MemberLoginRequestDto;
import cart.dto.product.ProductsResponseDto;
import cart.repository.member.MemberRepository;
import cart.service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static cart.factory.product.ProductFactory.createProduct;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
class CartControllerWebMvcTest {

    @MockBean
    private CartService cartService;

    @MockBean
    private LoginMemberArgumentResolver loginMemberArgumentResolver;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("사용자의 장바구니 정보를 가져온다.")
    void returns_member_carts() throws Exception {
        // given
        String authHeaderValue = "Basic dGVzdDFAdGVzdC5jb206ISFhYmMxMjM=";
        ProductsResponseDto expected = ProductsResponseDto.from(List.of(createProduct()));
        given(cartService.findAll(any(Member.class))).willReturn(expected);

        mockMvc.perform(get("/carts")
                        .header("Authorization", authHeaderValue))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.products[0].name").value("치킨"));

        // then
        verify(cartService).findAll(any(Member.class));
    }

    @Test
    @DisplayName("사용자의 장바구니 정보를 추가한다.")
    void add_member_carts() throws Exception {
        // given
        Long productId = 1L;
        String authHeaderValue = "Basic dGVzdDFAdGVzdC5jb206ISFhYmMxMjM=";

        mockMvc.perform(post("/carts/{id}", productId)
                        .header("Authorization", authHeaderValue))
                .andExpect(status().isCreated());

        // then
        verify(cartService).addCart(any(Member.class), eq(productId));
    }

    @Test
    @DisplayName("사용자의 장바구니 정보를 삭제한다.")
    void delete_member_carts() throws Exception {
        // given
        Long productId = 1L;
        String authHeaderValue = "Basic dGVzdDFAdGVzdC5jb206ISFhYmMxMjM=";

        mockMvc.perform(delete("/carts/{id}", productId)
                        .header("Authorization", authHeaderValue))
                .andExpect(status().isOk());

        // then
        verify(cartService).deleteCart(any(Member.class), eq(productId));
    }
}
