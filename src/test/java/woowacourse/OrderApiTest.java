package woowacourse;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class OrderApiTest extends TestSupport {

    @Test
    void addOrder_test() throws Exception {
        mockMvc.perform(
                post("/customers/orders")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .header("Authorization", "Bearer " + accessToken)
                    .content(readJson("/json/orders/orders-create.json"))
            )
            .andExpect(status().isCreated());
    }

    @Test
    void findOrder_test() throws Exception {
        mockMvc.perform(
                get("/customers/orders/{orderId}", 1L)
                    .header("Authorization", "Bearer " + accessToken)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andDo(
                restDocs.document(
                    pathParameters(
                        parameterWithName("orderId").description("order id")
                    ),
                    responseFields(
                        fieldWithPath("id").description("order id"),
                        fieldWithPath("orderDetails.[].productId").description("product id"),
                        fieldWithPath("orderDetails.[].quantity").description("product quantity"),
                        fieldWithPath("orderDetails.[].price").description("product price"),
                        fieldWithPath("orderDetails.[].name").description("product name"),
                        fieldWithPath("orderDetails.[].imageUrl").description("product imageUrl")
                    )
                )
            );
    }

    @Test
    void findOrders_test() throws Exception {
        mockMvc.perform(
                get("/customers/orders")
                    .header("Authorization", "Bearer " + accessToken)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andDo(
                restDocs.document(
                    responseFields(
                        fieldWithPath("[].id").description("order id"),
                        fieldWithPath("[].orderDetails.[].productId").description("product id"),
                        fieldWithPath("[].orderDetails.[].quantity").description(
                            "product quantity"),
                        fieldWithPath("[].orderDetails.[].price").description("product price"),
                        fieldWithPath("[].orderDetails.[].name").description("product name"),
                        fieldWithPath("[].orderDetails.[].imageUrl").description("product imageUrl")
                    )
                )
            );
    }
}
