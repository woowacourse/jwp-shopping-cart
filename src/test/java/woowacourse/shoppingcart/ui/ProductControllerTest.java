package woowacourse.shoppingcart.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static woowacourse.helper.restdocs.RestDocsUtils.getRequestPreprocessor;
import static woowacourse.helper.restdocs.RestDocsUtils.getResponsePreprocessor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import woowacourse.helper.restdocs.RestDocsTest;
import woowacourse.shoppingcart.dto.ProductRequest;

@DisplayName("product 컨트롤러 단위테스트")
public class ProductControllerTest extends RestDocsTest {

    @DisplayName("새 상품을 추가한다.")
    @Test
    void add() throws Exception {
        ProductRequest productRequest = new ProductRequest("김치", 1000, "image.com");

        given(productService.addProduct(any(ProductRequest.class))).willReturn(1L);
        final ResultActions resultActions = mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "/api/products/1"));

        //docs
        resultActions.andDo(document("products-create",
                getRequestPreprocessor(),
                getResponsePreprocessor(),
                requestFields(
                        fieldWithPath("name").type(STRING).description("제품명"),
                        fieldWithPath("price").type(NUMBER).description("가격"),
                        fieldWithPath("imageUrl").type(STRING).description("이미지 url")
                )));
    }
}
