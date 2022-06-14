package woowacourse;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class CartItemApiTest extends TestSupport {

    @Test
    void getCartItems_test() throws Exception {
        mockMvc.perform(
                get("/customers/carts")
                    .header("Authorization", "Bearer " + accessToken)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andDo(
                restDocs.document(
                    requestHeaders(
                        headerWithName("Authorization").description("Authorization")
                    ),
                    responseFields(
                        fieldWithPath("[].id").description("cart id"),
                        fieldWithPath("[].quantity").description("product quantity"),
                        fieldWithPath("[].product.id").description("product id"),
                        fieldWithPath("[].product.name").description("product name"),
                        fieldWithPath("[].product.price").description("product price"),
                        fieldWithPath("[].product.imageUrl").description("product imageUrl")
                    )
                )
            );
    }

    @Test
    void addCartItem_test() throws Exception {
        mockMvc.perform(
                post("/customers/carts")
                    .header("Authorization", "Bearer " + accessToken)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(readJson("/json/carts/carts-create.json"))
            )
            .andExpect(status().isCreated())
            .andDo(
                restDocs.document(
                    requestHeaders(
                        headerWithName("Authorization").description("Authorization")
                    ),
                    responseFields(
                        fieldWithPath("id").description("cart id"),
                        fieldWithPath("product.id").description("product id"),
                        fieldWithPath("product.name").description("product name"),
                        fieldWithPath("product.price").description("product price"),
                        fieldWithPath("product.imageUrl").description("product imageUrl"),
                        fieldWithPath("quantity").description("product quantity")
                    )
                )
            );
    }

    @Test
    void deleteCartItem_test() throws Exception {
        mockMvc.perform(
                delete("/customers/carts/{cartId}", 1L)
                    .header("Authorization", "Bearer " + accessToken)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
            )
            .andExpect(status().isNoContent())
            .andDo(restDocs.document(
                    pathParameters(
                        parameterWithName("cartId").description("cart id")
                    )
                )
            );
    }

    @Test
    void updateCartItemQuantity_test() throws Exception {
        mockMvc.perform(
                put("/customers/carts/{cartId}", 1L)
                    .header("Authorization", "Bearer " + accessToken)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(readJson("/json/carts/carts-quantity-update.json"))
            )
            .andExpect(status().isOk())
            .andDo(restDocs.document(
                pathParameters(
                    parameterWithName("cartId").description("cart id")
                ),
                responseFields(
                    fieldWithPath("id").description("cart id"),
                    fieldWithPath("product.id").description("product id"),
                    fieldWithPath("product.name").description("product name"),
                    fieldWithPath("product.price").description("product price"),
                    fieldWithPath("product.imageUrl").description("product imageUrl"),
                    fieldWithPath("quantity").description("product quantity")
                )
            ));
    }

    @Test
    void deleteAllCart_test() throws Exception {
        mockMvc.perform(
                delete("/customers/carts")
                    .header("Authorization", "Bearer " + accessToken)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
            )
            .andExpect(status().isNoContent());
    }
}
