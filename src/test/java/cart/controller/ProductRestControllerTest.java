package cart.controller;

import static cart.domain.product.ProductFixture.NUNU_ID_PRODUCT;
import static cart.domain.product.ProductFixture.ODO_ID_PRODUCT;
import static cart.dto.ResponseFixture.NUNU_RESPONSE;
import static cart.dto.ResponseFixture.ODO_RESPONSE;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.domain.product.Product;
import cart.dto.ProductRequest;
import cart.dto.ProductResponse;
import cart.dto.RequestFixture;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

@SuppressWarnings({"NonAsciiCharacters", "SpellCheckingInspection"})
class ProductRestControllerTest extends AbstractProductControllerTest {

    @Test
    void 상품_생성_테스트() throws Exception {
        // given
        given(productCommandService.create(anyString(), anyString(), anyInt())).willReturn(NUNU_ID_PRODUCT);
        final ProductRequest productRequest = RequestFixture.NUNU_REQUEST;
        final String request = objectMapper.writeValueAsString(productRequest);
        final String result = objectMapper.writeValueAsString(NUNU_RESPONSE);

        // when
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                // then
                .andExpect(status().isCreated())
                .andExpect(content().json(result));
    }

    @Test
    void 상품_삭제_테스트() throws Exception {
        mockMvc.perform(delete("/products/1"))

                .andExpect(status().isNoContent());
    }

    @Test
    void 상품_업데이트_테스트() throws Exception {
        // given
        given(productCommandService.update(anyLong(), anyString(), anyString(), anyInt())).willReturn(NUNU_ID_PRODUCT);
        final ProductRequest productRequest = RequestFixture.NUNU_REQUEST;
        final String request = objectMapper.writeValueAsString(productRequest);
        final String result = objectMapper.writeValueAsString(NUNU_RESPONSE);
        final int id = 1;

        // when
        mockMvc.perform(put("/products/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                // then
                .andExpect(status().isOk())
                .andExpect(content().json(result));
    }

    @Test
    void 상품_조회_테스트() throws Exception {
        final List<Product> given = List.of(NUNU_ID_PRODUCT, ODO_ID_PRODUCT);
        given(productQueryService.find()).willReturn(given);
        final List<ProductResponse> productResponses = List.of(NUNU_RESPONSE, ODO_RESPONSE);
        final String result = objectMapper.writeValueAsString(productResponses);

        mockMvc.perform(get("/products"))

                .andExpect(status().isOk())
                .andExpect(content().json(result));
    }

    @Test
    void PUT_시에_상품_이름_길이_초과_테스트() throws Exception {
        final ProductRequest productRequest = new ProductRequest("a".repeat(256), "naver.com", 1);
        final String request = objectMapper.writeValueAsString(productRequest);

        mockMvc.perform(put("/products/1")
                        .contentType("application/json")
                        .content(request))

                .andExpect(status().isBadRequest());
    }

    @Test
    void PUT_시에_상품_이름_공백_테스트() throws Exception {
        final ProductRequest productRequest = new ProductRequest("", "naver.com", 1);
        final String request = objectMapper.writeValueAsString(productRequest);

        mockMvc.perform(put("/products/1")
                        .contentType("application/json")
                        .content(request))

                .andExpect(status().isBadRequest());
    }

    @Test
    void PUT_시에_이미지_url_길이_초과_테스트() throws Exception {
        final ProductRequest productRequest = new ProductRequest("누누", "a".repeat(2049), 1);
        final String request = objectMapper.writeValueAsString(productRequest);

        mockMvc.perform(put("/products/1")
                        .contentType("application/json")
                        .content(request))

                .andExpect(status().isBadRequest());
    }

    @Test
    void PUT_시에_이미지_url_공백_테스트() throws Exception {
        final ProductRequest productRequest = new ProductRequest("누누", "", 1);
        final String request = objectMapper.writeValueAsString(productRequest);

        mockMvc.perform(put("/products/1")
                        .contentType("application/json")
                        .content(request))

                .andExpect(status().isBadRequest());
    }

    @Test
    void PUT_시에_상품_가격_음수_예외_테스트() throws Exception {
        final ProductRequest productRequest = new ProductRequest("누누", "naver.com", -1);
        final String request = objectMapper.writeValueAsString(productRequest);

        mockMvc.perform(put("/products/1")
                        .contentType("application/json")
                        .content(request))

                .andExpect(status().isBadRequest());
    }


    @Test
    void POST_시에_상품_이름_길이_초과_테스트() throws Exception {
        final ProductRequest productRequest = new ProductRequest("a".repeat(256), "naver.com", 1);
        final String request = objectMapper.writeValueAsString(productRequest);

        mockMvc.perform(post("/products")
                        .contentType("application/json")
                        .content(request))

                .andExpect(status().isBadRequest());
    }

    @Test
    void POST_시에_상품_이름_공백_테스트() throws Exception {
        final ProductRequest productRequest = new ProductRequest("", "naver.com", 1);
        final String request = objectMapper.writeValueAsString(productRequest);

        mockMvc.perform(post("/products")
                        .contentType("application/json")
                        .content(request))

                .andExpect(status().isBadRequest());
    }

    @Test
    void POST_시에_이미지_url_길이_초과_테스트() throws Exception {
        final ProductRequest productRequest = new ProductRequest("누누", "a".repeat(2049), 1);
        final String request = objectMapper.writeValueAsString(productRequest);

        mockMvc.perform(post("/products")
                        .contentType("application/json")
                        .content(request))

                .andExpect(status().isBadRequest());
    }

    @Test
    void POST_시에_이미지_url_공백_테스트() throws Exception {
        final ProductRequest productRequest = new ProductRequest("누누", "", 1);
        final String request = objectMapper.writeValueAsString(productRequest);

        mockMvc.perform(post("/products")
                        .contentType("application/json")
                        .content(request))

                .andExpect(status().isBadRequest());
    }

    @Test
    void POST_시에_상품_가격_음수_예외_테스트() throws Exception {
        final ProductRequest productRequest = new ProductRequest("누누", "naver.com", -1);
        final String request = objectMapper.writeValueAsString(productRequest);

        mockMvc.perform(post("/products")
                        .contentType("application/json")
                        .content(request))

                .andExpect(status().isBadRequest());
    }
}
