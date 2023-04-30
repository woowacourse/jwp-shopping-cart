package cart.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.configuration.AuthenticationPrincipalArgumentResolver;
import cart.controller.dto.request.AddCartRequest;
import cart.controller.dto.AuthInfo;
import cart.exception.GlobalControllerAdvice;
import cart.exception.auth.NotSignInException;
import cart.exception.cart.CartNotFoundException;
import cart.exception.item.ItemNotFoundException;
import cart.exception.user.UserNotFoundException;
import cart.service.CartService;
import cart.service.dto.CartDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

@WebMvcTest(controllers = CartController.class)
@Transactional
class CartControllerTest {

    @MockBean
    CartService cartService;

    @MockBean
    AuthenticationPrincipalArgumentResolver authArgumentResolver;

    @Autowired
    CartController cartController;

    MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(cartController).setControllerAdvice(new GlobalControllerAdvice())
                .setCustomArgumentResolvers(authArgumentResolver).alwaysDo(print()).build();
    }

    @Test
    @DisplayName("로그인한 사용자의 장바구니를 모두 조회한다.")
    void findAllCartsSuccess() throws Exception {
        given(authArgumentResolver.supportsParameter(any(MethodParameter.class))).willReturn(true);
        given(authArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(createAuthInfo());
        given(cartService.findAllByEmail(anyString())).willReturn(List.of(createCartDto()));

        mockMvc.perform(get("/carts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    @DisplayName("로그인하지 않고 장바구니를 모두 조회하면 UNAUTHORIZED를 반환한다.")
    void findAllCartsFailWithNotSignIn() throws Exception {
        given(authArgumentResolver.supportsParameter(any(MethodParameter.class))).willReturn(true);
        given(authArgumentResolver.resolveArgument(any(), any(), any(), any())).willThrow(
                new NotSignInException("로그인이 필요한 기능입니다."));
        given(cartService.findAllByEmail(anyString())).willReturn(List.of(createCartDto()));

        mockMvc.perform(get("/carts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("로그인이 필요한 기능입니다."));
    }

    @Test
    @DisplayName("로그인한 사용자의 장바구니에 상품을 추가한다.")
    void addCartSuccess() throws Exception {
        given(authArgumentResolver.supportsParameter(any(MethodParameter.class))).willReturn(true);
        given(authArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(createAuthInfo());
        given(cartService.add(anyString(), anyLong())).willReturn(createCartDto());
        AddCartRequest addCartRequest = new AddCartRequest(1L);

        mockMvc.perform(post("/carts")
                        .content(objectMapper.writeValueAsString(addCartRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isCreated()).andExpect(header().string("Location", "/carts/1"))
                .andExpect(jsonPath("$.cartId").value(1)).andExpect(jsonPath("$.itemName").value("a"))
                .andExpect(jsonPath("$.itemImageUrl").value("https://image.com"))
                .andExpect(jsonPath("$.itemPrice").value(10_000));
    }

    @Test
    @DisplayName("방바구니에 추가할 상품을 입력하지 않으면 BAD REQUEST를 반환한다.")
    void addCartFailWithBlankItemId() throws Exception {
        given(authArgumentResolver.supportsParameter(any(MethodParameter.class))).willReturn(true);
        given(authArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(createAuthInfo());
        AddCartRequest addCartRequest = new AddCartRequest(null);

        mockMvc.perform(post("/carts")
                        .content(objectMapper.writeValueAsString(addCartRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("상품 ID를 필수적으로 입력해주세요."));
    }


    @Test
    @DisplayName("장바구니에 없는 상품을 추가하려고 하는 경우 BAD REQUEST를 반환한다.")
    void addCartFailWithNotExistsItemId() throws Exception {
        given(authArgumentResolver.supportsParameter(any(MethodParameter.class))).willReturn(true);
        given(authArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(createAuthInfo());
        given(cartService.add(anyString(), anyLong()))
                .willThrow(new ItemNotFoundException("일치하는 상품을 찾을 수 없습니다."));
        AddCartRequest addCartRequest = new AddCartRequest(1L);

        mockMvc.perform(post("/carts")
                        .content(objectMapper.writeValueAsString(addCartRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("일치하는 상품을 찾을 수 없습니다."));
    }

    @Test
    @DisplayName("장바구니에 상품을 추가하려고 하는 사용자가 없는 사용자인 경우 BAD REQUEST를 반환한다.")
    void addCartFailWithNotExistsUser() throws Exception {
        given(authArgumentResolver.supportsParameter(any(MethodParameter.class))).willReturn(true);
        given(authArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(createAuthInfo());
        given(cartService.add(anyString(), anyLong())).willThrow(new UserNotFoundException("존재하지 않는 사용자입니다."));
        AddCartRequest addCartRequest = new AddCartRequest(1L);

        mockMvc.perform(post("/carts")
                        .content(objectMapper.writeValueAsString(addCartRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("존재하지 않는 사용자입니다."));
    }

    @Test
    @DisplayName("로그인하지 않은 상태로 장바구니에 상품을 추가하려고 하면 UNAUTHORIZED를 반환한다.")
    void addCartFailWithNotSignIn() throws Exception {
        given(authArgumentResolver.supportsParameter(any(MethodParameter.class))).willReturn(true);
        given(authArgumentResolver.resolveArgument(any(), any(), any(), any())).willThrow(
                new NotSignInException("로그인이 필요한 기능입니다."));
        given(cartService.findAllByEmail(anyString())).willReturn(
                List.of(createCartDto()));
        AddCartRequest addCartRequest = new AddCartRequest(1L);

        mockMvc.perform(post("/carts")
                        .content(objectMapper.writeValueAsString(addCartRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("로그인이 필요한 기능입니다."));
    }

    @Test
    @DisplayName("장바구니에 등록된 상품을 삭제한다.")
    void deleteCartSuccess() throws Exception {
        given(authArgumentResolver.supportsParameter(any(MethodParameter.class))).willReturn(true);
        given(authArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(createAuthInfo());
        willDoNothing().given(cartService).delete(anyLong(), anyString());

        mockMvc.perform(delete("/carts/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("장바구니에 존재하지 않는 상품을 삭제하려고 하면 NOT FOUND를 반환한다.")
    void deleteCartFailWithNotExistsCartId() throws Exception {
        given(authArgumentResolver.supportsParameter(any(MethodParameter.class))).willReturn(true);
        given(authArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(createAuthInfo());
        willThrow(new CartNotFoundException("장바구니에 존재하지 않는 상품입니다."))
                .given(cartService).delete(anyLong(), anyString());

        mockMvc.perform(delete("/carts/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("장바구니에 존재하지 않는 상품입니다."));
    }

    @Test
    @DisplayName("로그인하지 않은 상태로 장바구니의 상품을 삭제하려고 하면 UNAUTHORIZED를 반환한다.")
    void deleteCartFailWithNotSignIn() throws Exception {
        given(authArgumentResolver.supportsParameter(any(MethodParameter.class))).willReturn(true);
        given(authArgumentResolver.resolveArgument(any(), any(), any(), any())).willThrow(
                new NotSignInException("로그인이 필요한 기능입니다."));
        given(cartService.findAllByEmail(anyString())).willReturn(
                List.of(createCartDto()));
        AddCartRequest addCartRequest = new AddCartRequest(1L);

        mockMvc.perform(delete("/carts/{id}", 1L)
                        .content(objectMapper.writeValueAsString(addCartRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("로그인이 필요한 기능입니다."));
    }

    private CartDto createCartDto() {
        return new CartDto(1L, 1L, "a", "https://image.com", 10_000);
    }

    private AuthInfo createAuthInfo() {
        return new AuthInfo("a@a.com", "a");
    }
}
