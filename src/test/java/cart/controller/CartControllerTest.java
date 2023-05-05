package cart.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

import cart.dao.UserDao;
import cart.domain.CartItem;
import cart.domain.Product;
import cart.domain.User;
import cart.service.CartService;

@WebMvcTest(CartController.class)
class CartControllerTest {

    private static final int USER_ID = 777;
    private static final int OTHER_USER_ID = 888;
    private static final String EMAIL = "email@wooteco.com";
    private static final String OTHER_EMAIL = "0chil@wooteco.com";
    private static final String PASSWORD = "password";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDao userDao;
    @MockBean
    private CartService cartService;

    @BeforeEach
    void setUp() {
        given(userDao.selectBy(anyString()))
                .willThrow(new EmptyResultDataAccessException(1));
        given(userDao.selectBy(eq(EMAIL)))
                .willReturn(new User(USER_ID, EMAIL, PASSWORD));
        given(userDao.selectBy(eq(OTHER_EMAIL)))
                .willReturn(new User(OTHER_USER_ID, OTHER_EMAIL, PASSWORD));

        given(cartService.getCartItemsOf(any()))
                .willReturn(Collections.emptyList());
        given(cartService.getCartItemsOf(eq(USER_ID)))
                .willReturn(List.of(
                        new CartItem(1, new Product(1, "밀키스", "image1.png", 2000L)),
                        new CartItem(2, new Product(2, "버터링", "image2.png", 2300L))
                ));
    }

    @DisplayName("유저마다 다른 장바구니가 응답된다")
    @Test
    void cartResponseIsDifferentDependingOnUser() throws Exception {
        String userCartItems = mockMvc.perform(get("/cart/items")
                        .header("Authorization", "Basic " + encode(EMAIL + ":" + PASSWORD))
                )
                .andReturn().getResponse().getContentAsString();

        String otherUserCartItems = mockMvc.perform(get("/cart/items")
                        .header("Authorization", "Basic " + encode(OTHER_EMAIL + ":" + PASSWORD))
                )
                .andReturn().getResponse().getContentAsString();

        assertThat(userCartItems).isNotEqualTo(otherUserCartItems);
    }

    @DisplayName("인증 정보가 없으면 UNAUTHORIZED가 응답된다")
    @Test
    void emptyAuthenticationResponsesWithUnauthorized() throws Exception {
        mockMvc.perform(get("/cart/items"))
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("인증 정보에 이메일이 없으면 UNAUTHORIZED가 응답된다")
    @Test
    void noEmailInAuthenticationResponsesWithUnauthorized() throws Exception {
        mockMvc.perform(get("/cart/items")
                        .header("Authorization", "Basic " + encode(":password"))
                )
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("인증 정보에 비밀번호가 없으면 UNAUTHORIZED가 응답된다")
    @Test
    void noPasswordInAuthenticationResponsesWithUnauthorized() throws Exception {
        mockMvc.perform(get("/cart/items")
                        .header("Authorization", "Basic " + encode("email:"))
                )
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("없는 이메일이면 UNAUTHORIZED가 응답된다")
    @Test
    void invalidEmailAuthenticationResponsesWithUnauthorized() throws Exception {
        mockMvc.perform(get("/cart/items")
                        .header("Authorization", "Basic " + encode("invalid@email.com:pass"))
                )
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("비밀번호가 틀리면 UNAUTHORIZED가 응답된다")
    @Test
    void wrongPasswordAuthenticationResponsesWithUnauthorized() throws Exception {
        mockMvc.perform(get("/cart/items")
                        .header("Authorization", "Basic " + encode(EMAIL + ":pass"))
                )
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("정상 인증 정보는 OK가 응답된다")
    @Test
    void validAuthenticationResponsesWithOk() throws Exception {
        mockMvc.perform(get("/cart/items")
                        .header("Authorization", "Basic " + encode(EMAIL + ":" + PASSWORD))
                )
                .andExpect(status().isOk());
    }

    private String encode(String string) {
        return new String(Base64Utils.encode(string.getBytes()));
    }
}