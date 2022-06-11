package woowacourse.shoppingcart.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static woowacourse.shoppingcart.ApiDocumentUtils.getDocumentRequest;
import static woowacourse.shoppingcart.ApiDocumentUtils.getDocumentResponse;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import woowacourse.shoppingcart.RestDocsTokenProvider;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.dto.ProductResponse;

@WebMvcTest(CartItemController.class)
class CartItemControllerTest extends RestDocsTokenProvider {

    @MockBean
    private CartService cartService;

    @Test
    void get() throws Exception {

        ProductResponse bananaResponse = new ProductResponse(1L, "바나나", 1000, "banana.com");
        ProductResponse appleResponse = new ProductResponse(2L, "사과", 1500, "apple.com");

        CartResponse cartResponse= new CartResponse(List.of(bananaResponse, appleResponse));
        given(cartService.findCartsByCustomerId(any(Long.class))).willReturn(cartResponse);

        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/customers/cart")
                        .header("Authorization", "bearer access-token"))
                .andExpect(status().isOk())
                .andDo(document("customer-cart-get",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(headerWithName("Authorization").description("access token")),
                        responseFields(
                                fieldWithPath("cart").type(JsonFieldType.ARRAY).description("카트"),
                                fieldWithPath("cart[].id").type(JsonFieldType.NUMBER).description("상품 아이디"),
                                fieldWithPath("cart[].name").type(JsonFieldType.STRING).description("상품 이름"),
                                fieldWithPath("cart[].price").type(JsonFieldType.NUMBER).description("상품 가격"),
                                fieldWithPath("cart[].imageUrl").type(JsonFieldType.STRING).description("상품 이미지 경로")
                        )
                ));
    }

    @Test
    void create() throws Exception {

        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/customers/cart")
                        .header("Authorization", "bearer access-token")
                        .content("{\n"
                                + "    \"productId\": 1\n"
                                + "}")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andDo(document("customer-cart-create",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(headerWithName("Authorization").description("access token")),
                        requestFields(
                                fieldWithPath("productId").type(JsonFieldType.NUMBER).description("카트")
                        )
                ));
    }

    @Test
    void delete() throws Exception {

        this.mockMvc.perform(RestDocumentationRequestBuilders.delete("/customers/cart")
                        .header("Authorization", "bearer access-token")
                        .content("{\n"
                                + "    \"productId\": 1\n"
                                + "}")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent())
                .andDo(document("customer-cart-delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(headerWithName("Authorization").description("access token")),
                        requestFields(
                                fieldWithPath("productId").type(JsonFieldType.NUMBER).description("카트")
                        )
                ));
    }
}
