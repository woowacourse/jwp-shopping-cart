package woowacourse.shoppingcart.ui;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static woowacourse.Fixture.페퍼_아이디;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dto.CartItemCreateRequest;
import woowacourse.shoppingcart.dto.CartItemUpdateRequest;

@AutoConfigureMockMvc
@SpringBootTest
@Sql("/truncate.sql")
class CartItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("장바구니에 상품을 추가할 때 productId가 null이면, Bad Request를 던진다.")
    void add() throws Exception {
        // given
        CartItemCreateRequest request = new CartItemCreateRequest(null);
        String requestContent = objectMapper.writeValueAsString(request);
        String accessToken = jwtTokenProvider.createToken(페퍼_아이디);

        // when
        final ResultActions response = mockMvc.perform(post("/customers/carts")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andDo(print());

        // then
        response.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("상품 수량을 0미만으로 설정하여 장바구니 아이템 수량 수정을 요청하면, Bad Request를 던진다.")
    void updateQuantity() throws Exception {
        // given
        CartItemUpdateRequest request = new CartItemUpdateRequest(0);
        String requestContent = objectMapper.writeValueAsString(request);
        String accessToken = jwtTokenProvider.createToken(페퍼_아이디);

        // when
        final ResultActions response = mockMvc.perform(put("/customers/carts/" + 1L)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andDo(print());

        // then
        response.andExpect(status().isBadRequest());
    }
}
