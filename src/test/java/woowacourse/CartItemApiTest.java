package woowacourse;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
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
                get("/api/customers/{customerName}/carts", "sunhpark42")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andDo(
                restDocs.document(
                    pathParameters(
                        parameterWithName("customerName").description("customer name")
                    ),
                    responseFields(
                        fieldWithPath("[].id").description("cart id"),
                        fieldWithPath("[].productId").description("product id"),
                        fieldWithPath("[].name").description("product name"),
                        fieldWithPath("[].price").description("product price"),
                        fieldWithPath("[].imageUrl").description("product imageUrl")
                    )
                )
            );
    }

    @Test
    void addCartItem_test() throws Exception {
        mockMvc.perform(
                post("/api/customers/{customerName}/carts", "sunhpark42")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(readJson("/json/carts/carts-create.json"))
            )
            .andExpect(status().isCreated())
            .andDo(restDocs.document(
                    pathParameters(
                        parameterWithName("customerName").description("customer name")
                    )
                )
            );
    }

    @Test
    void deleteCartItem_test() throws Exception {
        mockMvc.perform(
                delete("/api/customers/{customerName}/carts/{cartId}", "sunhpark42", 1L)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
            )
            .andExpect(status().isNoContent())
            .andDo(restDocs.document(
                    pathParameters(
                        parameterWithName("customerName").description("customer name"),
                        parameterWithName("cartId").description("cart id")
                    )
                )
            );
    }
}
