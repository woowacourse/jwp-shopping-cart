package woowacourse.shoppingcart.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static woowacourse.shoppingcart.ApiDocumentUtils.getDocumentRequest;
import static woowacourse.shoppingcart.ApiDocumentUtils.getDocumentResponse;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.auth.support.RequestTokenContext;
import woowacourse.shoppingcart.application.ProductService;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.dto.ProductsResponse;

@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriHost = "13.125.246.196")
@WebMvcTest(ProductController.class)
@Import({RequestTokenContext.class, JwtTokenProvider.class})
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void getProducts() throws Exception {

        ProductResponse bananaResponse = new ProductResponse(1L, "바나나", 1000, "banana.com");
        ProductResponse appleResponse = new ProductResponse(2L, "사과", 1500, "apple.com");
        ProductsResponse productsResponse = new ProductsResponse(List.of(bananaResponse, appleResponse));
        given(productService.findProducts()).willReturn(productsResponse);

        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/products"))
                .andExpect(status().isOk())
                .andDo(document("products-get",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("products").type(JsonFieldType.ARRAY).description("상품"),
                                fieldWithPath("products[].id").type(JsonFieldType.NUMBER).description("상품 아이디"),
                                fieldWithPath("products[].name").type(JsonFieldType.STRING).description("상품 이름"),
                                fieldWithPath("products[].price").type(JsonFieldType.NUMBER).description("상품 가격"),
                                fieldWithPath("products[].imageUrl").type(JsonFieldType.STRING).description("상품 이미지 경로")
                        )
                ));
    }

    @Test
    void getProduct() throws Exception {

        ProductResponse bananaResponse = new ProductResponse(1L, "바나나", 1000, "banana.com");
        given(productService.findProductById(any(Long.class))).willReturn(bananaResponse);

        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/products/{productId}", 1))
                .andExpect(status().isOk())
                .andDo(document("product-get",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("productId").description("상품 아이디")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("상품 아이디"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("상품 이름"),
                                fieldWithPath("price").type(JsonFieldType.NUMBER).description("상품 가격"),
                                fieldWithPath("imageUrl").type(JsonFieldType.STRING).description("상품 이미지 경로")
                        )
                ));
    }

    @Test
    void createProduct() throws Exception {

        ProductResponse bananaResponse = new ProductResponse(1L, "바나나", 1000, "banana.com");
        given(productService.addProduct(any(ProductRequest.class))).willReturn(bananaResponse);
        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/products")
                        .content("{\n"
                                + "    \"name\": \"banana\",\n"
                                + "    \"price\": 1000,\n"
                                + "    \"imageUrl\": \"banana.com\"\n"
                                + "}")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andDo(document("products-create",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("상품 이름"),
                                fieldWithPath("price").type(JsonFieldType.NUMBER).description("상품 가격"),
                                fieldWithPath("imageUrl").type(JsonFieldType.STRING).description("상품 이미지 경로")
                        )
                ));
    }

    @Test
    void deleteProduct() throws Exception {

        this.mockMvc.perform(RestDocumentationRequestBuilders.delete("/products/{productId}", 1))
                .andExpect(status().isNoContent())
                .andDo(document("product-delete",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                pathParameters(
                                        parameterWithName("productId").description("상품 아이디")
                                )
                        )
                );
    }
}


