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
import woowacourse.shoppingcart.application.ProductService;
import woowacourse.shoppingcart.ui.dto.ProductAddRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @MockBean
    private AuthService authService;

    @MockBean
    private AuthenticationPrincipalConfig authenticationPrincipalConfig;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("이름, 가격, url을 통해 상품을 등록할 수 있다.")
    void saveProduct() throws Exception {
        // given & when
        ResultActions 회원가입_응답 = postProduct("치킨", 20_000, "http://chicken.test.com");

        // then
        assertThat(회원가입_응답.andExpect(status().isOk()));
    }

    @Test
    @DisplayName("상품 등록시 이름, url에 공백, 가격이 양의 수가 아니라면 예외가 발생한다.")
    void checkUpdateRequestBlank() throws Exception {
        // given & when
        ResultActions 이름_공백1 = postProduct(null, 20_000, "http://chicken.test.com");
        ResultActions 이름_공백2 = postProduct(" ", 20_000, "http://chicken.test.com");
        ResultActions 가격_제로 = postProduct("치킨", 0, "http://chicken.test.com");
        ResultActions 가격_음수 = postProduct("치킨", -500, "http://chicken.test.com");
        ResultActions url_공백1 = postProduct("치킨", 20_000, null);
        ResultActions url_공백2 = postProduct("치킨", 20_000, " ");
        // then
        assertAll(
                () -> 이름_공백1.andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.message").value("[ERROR] 상품명은 공백일 수 없습니다.")),
                () -> 이름_공백2.andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.message").value("[ERROR] 상품명은 공백일 수 없습니다.")),
                () -> 가격_제로.andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.message").value("[ERROR] 가격은 양수입니다.")),
                () -> 가격_음수.andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.message").value("[ERROR] 가격은 양수입니다.")),
                () -> url_공백1.andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.message").value("[ERROR] 이미지 url은 공백일 수 없습니다.")),
                () -> url_공백2.andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.message").value("[ERROR] 이미지 url은 공백일 수 없습니다."))
        );
    }

    private ResultActions postProduct(String name, int price, String imageUrl) throws Exception {
        ProductAddRequest productAddRequest = new ProductAddRequest(name, price, imageUrl);
        return mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        objectMapper.writeValueAsString(productAddRequest)
                ));
    }
}