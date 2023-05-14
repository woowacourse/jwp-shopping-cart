package cart.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.auth.BasicAuthorizationExtractor;
import cart.controller.dto.CartResponse;
import cart.controller.dto.ProductResponse;
import cart.dao.CartDao;
import cart.dao.ProductDao;
import cart.domain.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@Import(BasicAuthorizationExtractor.class)
@WebMvcTest(CartController.class)
class CartControllerTest {

    @MockBean
    private ProductDao productDao;

    @MockBean
    private CartDao cartDao;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("addProduct의 POST가 정상적으로 요청된다.")
    @Test
    void addProduct() throws Exception {
        // when, then
        mockMvc.perform(post("/cart/products/{productId}", "1")
                        .header(HttpHeaders.AUTHORIZATION, "Basic amVvbXhvbkBnbWFpbC5jb206cGFzc3dvcmQxMjM="))
                .andExpect(status().isCreated());
    }

    @DisplayName("findAllProductsByMember의 GET요청과 Response가 정상적으로 작동한다.")
    @Test
    void findAllProductsByMember() throws Exception {
        // given
        Product product = new Product(1L, "닭다리", "image.url", 12000);
        CartResponse cartResponse = new CartResponse(List.of(ProductResponse.from(product)));

        given(cartDao.findCartItemsByMemberEmail(any())).willReturn(List.of(product));

        // when, then
        mockMvc.perform(get("/cart/products")
                        .header(HttpHeaders.AUTHORIZATION, "Basic amVvbXhvbkBnbWFpbC5jb206cGFzc3dvcmQxMjM="))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(objectMapper.writeValueAsString(cartResponse.getProductResponses())));
    }

    @DisplayName("removeProduct의 DELETE가 정상적으로 요청된다.")
    @Test
    void removeProduct() throws Exception {
        // when, then
        mockMvc.perform(delete("/cart/products/{productId}", "1")
                        .header(HttpHeaders.AUTHORIZATION, "Basic amVvbXhvbkBnbWFpbC5jb206cGFzc3dvcmQxMjM="))
                .andExpect(status().isNoContent());
    }
}
