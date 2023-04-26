package cart.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.domain.Product;
import cart.dto.ProductRequest;
import cart.dto.ProductResponse;
import cart.dto.RequestFixture;
import cart.dto.ResponseFixture;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

@WebMvcTest
class ProductCreateControllerTest extends AbstractProductControllerTest {

    @Test
    void 상품_생성_테스트() throws Exception {
        final Product product = new Product(1L, "누누", "naver.com", 1);
        given(productCreateService.create(anyString(), anyString(), anyInt())).willReturn(product);
        final ProductRequest productRequest = RequestFixture.NUNU_REQUEST;
        final String request = objectMapper.writeValueAsString(productRequest);
        final ProductResponse productResponse = ResponseFixture.NUNU_RESPONSE;
        final String result = objectMapper.writeValueAsString(productResponse);
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(content().json(result));
    }
}
