package cart.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.domain.Product;
import cart.dto.ProductRequest;
import cart.dto.ProductResponse;
import cart.dto.RequestFixture;
import cart.dto.ResponseFixture;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class ProductUpdateControllerTest extends AbstractProductControllerTest {

    @Test
    void 상품_업데이트_테스트() throws Exception {
        final Product product = new Product(1L, "누누", "naver.com", 1);
        given(productUpdateService.update(anyLong(), anyString(), anyString(), anyInt())).willReturn(product);

        final ProductRequest productRequest = RequestFixture.NUNU_REQUEST;
        final String request = objectMapper.writeValueAsString(productRequest);
        final ProductResponse productResponse = ResponseFixture.NUNU_RESPONSE;
        final String result = objectMapper.writeValueAsString(productResponse);
        final int id = 1;
        mockMvc.perform(put("/products/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(content().json(result));
    }
}
