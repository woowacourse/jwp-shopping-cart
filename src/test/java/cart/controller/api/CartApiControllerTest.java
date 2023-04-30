package cart.controller.api;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.controller.MockBasicAuthProviderConfig;
import cart.dto.cart.CartProductDto;
import cart.dto.product.ProductDto;
import cart.dto.request.cart.CartAddRequest;
import cart.dto.request.cart.CartDeleteRequest;
import cart.infrastructure.BasicAuthProvider;
import cart.infrastructure.User;
import cart.repository.CartDao;
import cart.repository.ProductDao;
import cart.service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CartApiController.class)
@Import(MockBasicAuthProviderConfig.class)
class CartApiControllerTest {
    private static final String AUTHORIZATION_HEADER = "authorization";
    private static final String TOKEN = "Basic Z2xlbmZpZGRpY2hAbmF2ZXIuY29tOjEyMzQ1Ng==";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @SpyBean
    CartService cartService;

    @MockBean
    CartDao cartDao;

    @MockBean
    ProductDao productDao;

    @Autowired
    BasicAuthProvider basicAuthProvider;

    @BeforeEach
    void setUp() {
        given(basicAuthProvider.resolveUser(anyString()))
                .willReturn(new User(1L, "glenfiddich@naver.com", "123456"));
    }

    @Test
    @DisplayName("/api/cart로 POST 요청과 상품의 ID를 보내면 HTTP 200 코드와 장바구니에 상품이 담겨야 한다.")
    void addProductToCart_success() throws Exception {
        // given
        CartAddRequest request = new CartAddRequest(1L);
        willDoNothing().given(cartService)
                .addToCart(1L, 1L);

        // expect
        mockMvc.perform(post("/api/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header(AUTHORIZATION_HEADER, TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("장바구니에 상품이 담겼습니다."));
    }

    @Test
    @DisplayName("장바구니에 상품을 담을 때 해당 상품이 없으면 HTTP 400 코드와 예외가 메시지가 반환되어야 한다.")
    void addProductToCart_invalidProduct() throws Exception {
        // given
        CartAddRequest request = new CartAddRequest(1L);
        given(productDao.existsById(1L))
                .willReturn(false);

        // expect
        mockMvc.perform(post("/api/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header(AUTHORIZATION_HEADER, TOKEN))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("존재하지 않는 상품의 ID 입니다."));
    }

    @Test
    @DisplayName("/api/cart로 GET 요청을 보내면 HTTP 200 코드와 함께 장바구니의 상품이 조회되어야 한다.")
    void findAllCartProducts_success() throws Exception {
        // given
        List<CartProductDto> productDtos = List.of(
                new CartProductDto(1L, new ProductDto(1L, "글렌피딕", 100_000, "image1")),
                new CartProductDto(2L, new ProductDto(1L, "글렌피딕", 100_000, "image1")),
                new CartProductDto(3L, new ProductDto(2L, "글렌리벳", 200_000, "image2"))
        );
        given(cartService.findAllCartProducts(1L))
                .willReturn(productDtos);

        // expect
        mockMvc.perform(get("/api/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION_HEADER, TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("3개의 상품이 조회되었습니다."))
                .andExpect(jsonPath("$.result[0].id").value(1))
                .andExpect(jsonPath("$.result[0].product.id").value(1))
                .andExpect(jsonPath("$.result[0].product.name").value("글렌피딕"))
                .andExpect(jsonPath("$.result[0].product.price").value(100_000))
                .andExpect(jsonPath("$.result[0].product.imageUrl").value("image1"))

                .andExpect(jsonPath("$.result[1].id").value(2))
                .andExpect(jsonPath("$.result[1].product.id").value(1))
                .andExpect(jsonPath("$.result[1].product.name").value("글렌피딕"))
                .andExpect(jsonPath("$.result[1].product.price").value(100_000))
                .andExpect(jsonPath("$.result[1].product.imageUrl").value("image1"))

                .andExpect(jsonPath("$.result[2].id").value(3))
                .andExpect(jsonPath("$.result[2].product.id").value(2))
                .andExpect(jsonPath("$.result[2].product.name").value("글렌리벳"))
                .andExpect(jsonPath("$.result[2].product.price").value(200_000))
                .andExpect(jsonPath("$.result[2].product.imageUrl").value("image2"));
    }

    @Test
    @DisplayName("/api/cart로 DELETE 요청을 보내면 HTTP 200 코드와 함께 장바구니의 상품이 삭제되어야 한다.")
    void deleteProductToCart_success() throws Exception {
        // given
        CartDeleteRequest request = new CartDeleteRequest(1L);
        willDoNothing().given(cartService)
                .deleteProduct(1L, 1L);

        // expect
        mockMvc.perform(delete("/api/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header(AUTHORIZATION_HEADER, TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("장바구니에 상품이 삭제되었습니다."));
    }

    @Test
    @DisplayName("장바구니에 상품을 삭제할 때 해당 상품이 없으면 HTTP 400 코드와 예외 메시지가 반환되어야 한다.")
    void deleteProductToCart_invalidProduct() throws Exception {
        // given
        CartDeleteRequest request = new CartDeleteRequest(1L);
        given(productDao.existsById(1L))
                .willReturn(false);

        // expect
        mockMvc.perform(delete("/api/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header(AUTHORIZATION_HEADER, TOKEN))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("존재하지 않는 장바구니의 ID 입니다."));
    }
}
