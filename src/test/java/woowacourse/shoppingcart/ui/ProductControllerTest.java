package woowacourse.shoppingcart.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static woowacourse.helper.fixture.ProductFixture.IMAGE;
import static woowacourse.helper.fixture.ProductFixture.NAME;
import static woowacourse.helper.fixture.ProductFixture.PRICE;
import static woowacourse.helper.restdocs.RestDocsUtils.getRequestPreprocessor;
import static woowacourse.helper.restdocs.RestDocsUtils.getResponsePreprocessor;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import woowacourse.helper.restdocs.RestDocsTest;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.exception.InvalidProductException;

@DisplayName("상품 컨트롤러 단위테스트")
class ProductControllerTest extends RestDocsTest {

    @DisplayName("상품 등록에 성공한다.")
    @Test
    void register() throws Exception {
        ProductRequest request = new ProductRequest(PRICE, NAME, IMAGE);

        given(productService.addProduct(any(ProductRequest.class))).willReturn(1L);

        ResultActions resultActions = mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        //docs
        resultActions.andDo(document("product-register",
                getRequestPreprocessor(),
                getResponsePreprocessor(),
                requestFields(
                        fieldWithPath("price").type(NUMBER).description("가격"),
                        fieldWithPath("name").type(STRING).description("이름"),
                        fieldWithPath("imageUrl").type(STRING).description("이미지")
                )));
    }

    @DisplayName("상품을 조회한다.")
    @Test
    void getProduct() throws Exception {
        ProductResponse productResponse = new ProductResponse(1L, NAME, PRICE, IMAGE);
        given(productService.findProductById(anyLong())).willReturn(productResponse);

        ResultActions resultActions = mockMvc.perform(get("/api/products/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(productResponse)));

        //docs
        resultActions.andDo(document("product-get",
                getRequestPreprocessor(),
                getResponsePreprocessor(),
                responseFields(
                        fieldWithPath("id").type(NUMBER).description("id"),
                        fieldWithPath("price").type(NUMBER).description("가격"),
                        fieldWithPath("name").type(STRING).description("이름"),
                        fieldWithPath("imageUrl").type(STRING).description("이미지")
                )));
    }

    @DisplayName("상품을 조회에 실패한다.")
    @Test
    void failedGetOrder() throws Exception {
        doThrow(InvalidProductException.class).when(productService).findProductById(anyLong());
        given(jwtTokenProvider.getPayload(anyString())).willReturn("1");
        given(jwtTokenProvider.validateToken(anyString())).willReturn(true);

        mockMvc.perform(get("/api/products/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("상품 목록을 조회한다.")
    @Test
    void getProducts() throws Exception {
        ProductResponse productResponse = new ProductResponse(1L, NAME, PRICE, IMAGE);
        given(productService.findProducts()).willReturn(List.of(productResponse));

        ResultActions resultActions = mockMvc.perform(get("/api/products")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(List.of(productResponse))));

        //docs
        resultActions.andDo(document("product-list-get",
                getRequestPreprocessor(),
                getResponsePreprocessor(),
                responseFields(
                        fieldWithPath("[].id").type(NUMBER).description("id"),
                        fieldWithPath("[].price").type(NUMBER).description("가격"),
                        fieldWithPath("[].name").type(STRING).description("이름"),
                        fieldWithPath("[].imageUrl").type(STRING).description("이미지")
                )));
    }

    @DisplayName("상품을 삭제한다.")
    @Test
    void deleteProduct() throws Exception {
        doNothing().when(productService).deleteProductById(anyLong());

        ResultActions resultActions = mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());

        resultActions.andDo(document("product-delete",
                getRequestPreprocessor(),
                getResponsePreprocessor()));
    }

    @DisplayName("상품 삭제에 실패한다.")
    @Test
    void failedDeleteProduct() throws Exception {
        doThrow(InvalidProductException.class).when(productService).deleteProductById(anyLong());
        given(jwtTokenProvider.getPayload(anyString())).willReturn("1");
        given(jwtTokenProvider.validateToken(anyString())).willReturn(true);

        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isBadRequest());
    }
}
