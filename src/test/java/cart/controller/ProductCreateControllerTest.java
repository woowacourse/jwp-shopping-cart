package cart.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.domain.Product;
import cart.domain.ProductFixture;
import cart.dto.ProductRequest;
import cart.dto.ProductResponse;
import cart.dto.RequestFixture;
import cart.dto.ResponseFixture;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

@SuppressWarnings({"NonAsciiCharacters"})
class ProductCreateControllerTest extends AbstractProductControllerTest {

    @Test
    void 상품_생성_테스트() throws Exception {
        // given
        final Product product = ProductFixture.NUNU_ID_PRODUCT;
        given(productCreateService.create(anyString(), anyString(), anyInt())).willReturn(product);
        final ProductRequest productRequest = RequestFixture.NUNU_REQUEST;
        final String request = objectMapper.writeValueAsString(productRequest);
        final ProductResponse productResponse = ResponseFixture.NUNU_RESPONSE;
        final String result = objectMapper.writeValueAsString(productResponse);

        // when
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                // then
                .andExpect(status().isCreated())
                .andExpect(content().json(result));
    }
}
