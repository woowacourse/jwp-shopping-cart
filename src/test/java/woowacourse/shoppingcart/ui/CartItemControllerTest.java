package woowacourse.shoppingcart.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.config.AuthenticationPrincipalConfig;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.global.exception.ErrorResponse;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.ui.dto.ProductChangeRequest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CartItemController.class)
class CartItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CartService cartService;

    @MockBean
    private AuthService authService;

    @MockBean
    private AuthenticationPrincipalConfig authenticationPrincipalConfig;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;


    @Test
    @DisplayName("수량 변경 시 수량이 양수가 아니라면 예외가 발생한다..")
    void checkRequestBlank() throws Exception {
        // given & when
        ResultActions 수량_체크1 = patchCustomers(0);
        ResultActions 수량_체크2 = patchCustomers(-500);

        // then
        assertAll(
                () -> 수량_체크1.andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.message").value("[ERROR] 수량은 양수입니다.")),
                () -> 수량_체크2.andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.message").value("[ERROR] 수량은 양수입니다."))
        );
    }

    private ResultActions patchCustomers(int quantity) throws Exception {
        ProductChangeRequest productChangeRequest = new ProductChangeRequest(quantity);

        return mockMvc.perform(patch("/api/carts/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        objectMapper.writeValueAsString(productChangeRequest)
                ));
    }
}