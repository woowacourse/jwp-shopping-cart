package woowacourse.shoppingcart.unit.product.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static woowacourse.shoppingcart.utils.ApiDocumentUtils.getDocumentRequest;
import static woowacourse.shoppingcart.utils.ApiDocumentUtils.getDocumentResponse;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import woowacourse.shoppingcart.product.domain.Product;
import woowacourse.shoppingcart.product.dto.ProductsResponse;
import woowacourse.shoppingcart.unit.ControllerTest;

class ProductControllerTest extends ControllerTest {

    @Test
    @DisplayName("상품 목록을 조회한다.")
    void products() throws Exception {
        // given
        final Product apple = new Product(1L, "사과", 1600, "apple.co.kr");
        final Product mango = new Product(2L, "망고", 700, "man.go");
        final Product peach = new Product(3L, "복숭아", 3100, "pea.ch");
        final Product orange = new Product(4L, "오렌지", 540, "orange.orgg");

        final List<Product> products = List.of(apple, mango, peach, orange);
        given(productService.findProducts())
                .willReturn(products);

        final ProductsResponse response = ProductsResponse.from(products);
        final String responseJson = objectMapper.writeValueAsString(response);

        // when
        final ResultActions perform = mockMvc.perform(
                get("/products")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.ALL)
        ).andDo(print());

        // then
        final String actualResponseJson = perform.andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThat(actualResponseJson).isEqualTo(responseJson);

        // docs
        perform.andDo(document("products",
                getDocumentRequest(),
                getDocumentResponse(),
                responseFields(
                        fieldWithPath("products[].id").description("상품 ID"),
                        fieldWithPath("products[].name").description("상품명"),
                        fieldWithPath("products[].price").description("상품 가격"),
                        fieldWithPath("products[].imageUrl").description("상품 사진 url")
                )
        ));
    }
}
