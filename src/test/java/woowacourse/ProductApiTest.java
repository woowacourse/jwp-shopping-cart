package woowacourse;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class ProductApiTest extends TestSupport {

    @Test
    void add_test() throws Exception {
        mockMvc.perform(
                post("/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(readJson("/json/products/products-create.json"))
            )
            .andExpect(status().isCreated())
            .andDo(
                restDocs.document(
                    requestFields(
                        fieldWithPath("name").description("name"),
                        fieldWithPath("price").description("price"),
                        fieldWithPath("imageUrl").description("imageUrl")
                    )
                )
            );
    }

    @Test
    void products_test() throws Exception {
        mockMvc.perform(
                get("/products")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andDo(
                restDocs.document(
                    responseFields(
                        fieldWithPath("[].id").description("product id"),
                        fieldWithPath("[].name").description("product name"),
                        fieldWithPath("[].price").description("product price"),
                        fieldWithPath("[].imageUrl").description("product imageUrl")
                    )
                )
            );
    }

    @Test
    void product_test() throws Exception {
        mockMvc.perform(
                get("/products/{productId}", 1L)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andDo(
                restDocs.document(
                    pathParameters(
                        parameterWithName("productId").description("Product Id")
                    ),
                    responseFields(
                        fieldWithPath("id").description("product id"),
                        fieldWithPath("name").description("product name"),
                        fieldWithPath("price").description("product price"),
                        fieldWithPath("imageUrl").description("product imageUrl")
                    )
                )
            );
    }

    @Test
    void delete_test() throws Exception {
        mockMvc.perform(
                delete("/products/{productId}", 2L)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
            )
            .andExpect(status().isNoContent())
            .andDo(
                restDocs.document(
                    pathParameters(
                        parameterWithName("productId").description("Product Id")
                    )
                )
            );
    }
}
