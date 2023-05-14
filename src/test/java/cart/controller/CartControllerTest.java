package cart.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.global.infrastructure.Credential;
import cart.service.CartService;
import cart.service.dto.cart.CartAddProductRequest;
import cart.service.dto.cart.CartAllProductSearchResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

@WebMvcTest(CartController.class)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CartService cartService;

    private String authorizationHeader;

    @BeforeEach
    void setUp() {
        String email = "email";
        String password = "password";
        String encodedInformation = Base64Utils.encodeToString((email + ":" + password).getBytes());
        authorizationHeader = "Basic " + encodedInformation;

    }

    @Test
    @DisplayName("searchAll() : cart의 모든 product를 조회한다.")
    void searchAll() throws Exception {
        // given
        final List<CartAllProductSearchResponse> cartAllProductSearchResponses =
                List.of(
                        new CartAllProductSearchResponse(1L, "피자", 10000, "image"),
                        new CartAllProductSearchResponse(2L, "치킨", 20000, "image2")
                );

        // when
        given(cartService.searchAllCartProducts(any()))
                .willReturn(cartAllProductSearchResponses);

        // expected
        mockMvc.perform(get("/carts/products")
                        .header(HttpHeaders.AUTHORIZATION, authorizationHeader))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("피자"))
                .andExpect(jsonPath("$[0].price").value(10000))
                .andExpect(jsonPath("$[0].imageUrl").value("image"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("치킨"))
                .andExpect(jsonPath("$[1].price").value(20000))
                .andExpect(jsonPath("$[1].imageUrl").value("image2"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("인증 정보가 없는 경우, 401 상태 코드를 응답한다.")
    void searchAllWithNotFoundAuthorization() throws Exception {
        // expected
        mockMvc.perform(get("/carts/products"))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("인증 헤더를 찾을 수 없습니다."));
    }

    @Test
    @DisplayName("지원하지 않는 헤더 포맷인 경우, 400 상태 코드를 응답한다.")
    void searchAllWithUnsupportedAuthorization() throws Exception {
        // given
        String wrongHeaderInformation = "email";

        // expected
        mockMvc.perform(get("/carts/products")
                        .header(HttpHeaders.AUTHORIZATION, wrongHeaderInformation))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("지원하지 않는 Authorization 헤더입니다."));
    }

    @Test
    @DisplayName("addProduct() : cart에 상품을 추가한다.")
    void addProduct() throws Exception {
        // given
        final CartAddProductRequest cartAddProductRequest = new CartAddProductRequest(1L);

        // when
        given(cartService.save(any(Credential.class), any(CartAddProductRequest.class)))
                .willReturn(1L);
        String requestBody = objectMapper.writeValueAsString(cartAddProductRequest);

        // expected
        mockMvc.perform(post("/carts/products")
                        .header(HttpHeaders.AUTHORIZATION, authorizationHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "/carts/products/1"));
    }

    @Test
    @DisplayName("deleteProduct() : cart에서 상품을 삭제한다.")
    void deleteProduct() throws Exception {
        // given
        final long deletedId = 1L;

        // when
        willDoNothing().given(cartService).deleteProduct(any(Long.class));

        // expected
        mockMvc.perform(delete("/carts/products/" + deletedId)
                        .header(HttpHeaders.AUTHORIZATION, authorizationHeader)
                )
                .andExpect(status().isOk());
    }
}
