package cart.controller;

import cart.controller.dto.CartDto;
import cart.exception.ForbiddenException;
import cart.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartRestController.class)
class CartRestControllerTest {

    private String authorization;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @BeforeEach
    void setUp() {
        authorization = "Basic am91cm5leUBnbWFpbC5jb206cGFzc3dvcmQ=";
    }

    @Test
    @DisplayName("장바구니에 상품을 추가한다.")
    void addCart_successs() throws Exception {
        // given
        when(cartService.addCart(any(), any())).thenReturn(1L);

        // when, then
        mockMvc.perform(post("/cart/{productId}", 1L)
                        .header("Authorization", authorization)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isCreated(),
                        header().string("Location", "/cart/me"));
    }

    @Test
    @DisplayName("장바구니 상품 추가 시 인증되지 않은 사용자면 예외가 발생한다.")
    void addCart_fail() throws Exception {
        mockMvc.perform(post("/cart/{productId}", 1L)
                        .header("Authorization", "")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isUnauthorized(),
                        jsonPath("$.errorMessage", contains("인증되지 않은 사용자입니다.")));
    }

    @Test
    @DisplayName("사용자의 장바구니 정보를 조회한다.")
    void getCartByMember() throws Exception {
        // given
        final CartDto chickenDto = new CartDto(1L, 1L, "치킨",
                "chicken_image_url", 20000, "KOREAN");
        final CartDto steakDto = new CartDto(1L, 2L, "스테이크",
                "steak_image_url", 40000, "WESTERN");
        final List<CartDto> cartDtos = List.of(chickenDto, steakDto);
        when(cartService.getProductsByMemberEmail(any())).thenReturn(cartDtos);

        // when, then
        mockMvc.perform(get("/cart/me")
                        .header("Authorization", authorization))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.[0].memberId").value(1L),
                        jsonPath("$.[0].productId").value(1L),
                        jsonPath("$.[0].productName").value("치킨"),
                        jsonPath("$.[0].productImageUrl").value("chicken_image_url"),
                        jsonPath("$.[0].productPrice").value(20000),
                        jsonPath("$.[0].productCategory").value("KOREAN"),
                        jsonPath("$.[1].memberId").value(1L),
                        jsonPath("$.[1].productId").value(2L),
                        jsonPath("$.[1].productName").value("스테이크"),
                        jsonPath("$.[1].productImageUrl").value("steak_image_url"),
                        jsonPath("$.[1].productPrice").value(40000),
                        jsonPath("$.[1].productCategory").value("WESTERN")
                );
    }

    @Test
    @DisplayName("사용자의 장바구니 상품을 제거한다.")
    void deleteCart_success() throws Exception {
        // given
        doNothing().when(cartService).deleteCart(any(), any(), any());

        // when, then
        mockMvc.perform(delete("/cart/{targetMemberId}/{productId}", 1L, 1L)
                        .header("Authorization", authorization)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("장바구니 상품 제거 시 인증되지 않은 사용자면 예외가 발생한다.")
    void deleteCart_unauthorized() throws Exception {
        mockMvc.perform(delete("/cart/{targetMemberId}/{productId}", 1L, 1L)
                        .header("Authorization", "")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isUnauthorized(),
                        jsonPath("$.errorMessage", contains("인증되지 않은 사용자입니다.")));
    }

    @Test
    @DisplayName("장바구니 상품 제거 시 권한이 없는 사용자면 예외가 발생한다.")
    void deleteCart_forbidden() throws Exception {
        // given
        doThrow(ForbiddenException.class).when(cartService).deleteCart(any(), any(), any());

        // when, then
        mockMvc.perform(delete("/cart/{targetMemberId}/{productId}", 1L, 1L)
                        .header("Authorization", "Basic dGVzdEBnbWFpbC5jb206dGVzdDEyMzQ=")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isForbidden(),
                        jsonPath("$.errorMessage", contains("권한이 없습니다.")));
    }
}
